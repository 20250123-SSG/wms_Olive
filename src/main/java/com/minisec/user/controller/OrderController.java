package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.order.*;
import com.minisec.user.service.CartService;
import com.minisec.user.service.OrderService;
import com.minisec.user.service.StoreService;
import com.minisec.user.view.printer.InsertStatusPrinter;
import com.minisec.user.view.printer.order.OrderDetailsPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderController {

    private final StoreService storeService = new StoreService();
    private final OrderService orderService = new OrderService();
    private final CartService cartService = new CartService();

    public OrderController() {
    }


    public List<StoreDto> selectStoreList() {
        return storeService.selectStoreList();
    }

    public List<StoreProductDto> selectStoreAllProductByStoreId(StoreDto storeDto) {
        return storeService.selectStoreAllProductByStoreId(storeDto.getStoreId());
    }
    

    public List<OrderDto> selectAllOrderListByUserId(Login user) {
        OrderDetailFilterDto orderDetailFilter = new OrderDetailFilterDto();
        orderDetailFilter.setUserId(user.getUserId());

        return orderService.selectAllOrderListByFilter(orderDetailFilter);
    }

    public void selectOneOrderDetailByOrderId(String inputOrderId,
                                               List<OrderDto> simpleOrderList){
        int orderId = Integer.parseInt(inputOrderId);

        simpleOrderList.stream() ///있나왁인해야도미
                .filter(orderDto -> orderDto.getOrderId() == orderId)
                .findFirst()
                .orElseThrow();

        OrderDetailFilterDto orderDetailFilter = new OrderDetailFilterDto();
        orderDetailFilter.setOrderId(orderId);

        List<OrderDto> resultOrder = orderService.selectAllOrderDetailListByFilter(orderDetailFilter);
        OrderDetailsPrinter.printOne(resultOrder.get(0));
    }

    public void selectCanceledStatusOrder(Login user) {
        OrderDetailFilterDto orderDetailFilter = new OrderDetailFilterDto();
        orderDetailFilter.setUserId(user.getUserId());
        orderDetailFilter.setOrderStatus(OrderStatus.OBSERVATION_IMPOSSIBLE.getValue());

        List<OrderDto> canceledOrderList = orderService.selectAllOrderDetailListByFilter(orderDetailFilter);
        OrderDetailsPrinter.printList(canceledOrderList);
    }


    public void insertOrder(List<OrderDto> orderList) {
        List<StoreInventoryDeductionDto> storeInventoryDeductionList = new ArrayList<>();
        List<UserBalanceUpdateDto> userAmountDeductionList = new ArrayList<>();

        for (OrderDto orderDto : orderList) {
            for (OrderProductDto orderProduct : orderDto.getOrderProducts()) {
                /// 1. store_detail UPDATE - 재고차감
                int storeDetailId = orderProduct.getProduct().getStoreProductId();
                int userOrderQuantity = orderProduct.getQuantity();
                storeInventoryDeductionList.add(new StoreInventoryDeductionDto(
                        storeDetailId,
                        userOrderQuantity
                ));
            }
            /// 2. user - 금액차감
            int userId = orderDto.getUserId();
            int totalPrice = orderDto.getTotalPrice();
            userAmountDeductionList.add(new UserBalanceUpdateDto(
                    userId,
                    Math.negateExact(totalPrice)
            ));
        }
        boolean insertResult = orderService.order(new OrderProcessDto(
                storeInventoryDeductionList,
                userAmountDeductionList,
                orderList
        ));
        if (!insertResult) {
            InsertStatusPrinter.printInsertOrderList(false);
            return;
        }

        InsertStatusPrinter.printInsertOrderList(true);
        selectAllOrderDetailListByOrderId(orderList);
    }

    private void selectAllOrderDetailListByOrderId(List<OrderDto> orderList) {
        List<OrderDetailFilterDto> orderDetailFilterList = new ArrayList<>();
        for (OrderDto orderDto : orderList) {
            orderDetailFilterList.add(OrderDetailFilterDto.builder()
                    .orderId(orderDto.getOrderId())
                    .build());
        }
        for (OrderDetailFilterDto orderDetailFilterDto : orderDetailFilterList) {
            orderList = orderService.selectAllOrderDetailListByFilter(orderDetailFilterDto);
            OrderDetailsPrinter.printList(orderList);
        }
    }

    public void insertCartList(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        List<CartDto> cartList = new ArrayList<>();

        for (Map.Entry<StoreDto, List<OrderProductDto>> entry : orderListByStore.entrySet()) {
            StoreDto storeDto = entry.getKey();
            List<OrderProductDto> orderProductDtoList = entry.getValue();

            for (OrderProductDto orderProductDto : orderProductDtoList) {
                cartList.add(new CartDto(
                        storeDto.getStoreId()
                        , user.getUserId()
                        , orderProductDto
                ));
            }
        }
        boolean insertResult = cartService.insertCartList(cartList);
        InsertStatusPrinter.printInsertCartList(insertResult);
    }

}
