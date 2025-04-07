package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.store.StoreDto;
import com.minisec.user.model.dto.store.StoreInventoryDeductionDto;
import com.minisec.user.model.dto.store.StoreProductDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.order.*;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;
import com.minisec.user.service.CartService;
import com.minisec.user.service.OrderService;
import com.minisec.user.service.StoreService;
import com.minisec.user.view.printer.ExceptionPrinter;
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


    public void selectOneOrderDetailByOrderId(String inputOrderId, List<OrderDto> simpleOrderList) {
        final int orderId;
        try {
            orderId = Integer.parseInt(inputOrderId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("주문 번호를 입력해주세요.");
        }

        simpleOrderList.stream()
                .filter(orderDto -> orderDto.getOrderId() == orderId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문번호입니다."));

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

                int storeDetailId = orderProduct.getProduct().getStoreProductId();
                int userOrderQuantity = orderProduct.getQuantity();
                storeInventoryDeductionList.add(new StoreInventoryDeductionDto(
                        storeDetailId,
                        userOrderQuantity
                ));
            }
            int userId = orderDto.getUserId();
            int totalPrice = orderDto.getTotalPrice();
            userAmountDeductionList.add(new UserBalanceUpdateDto(
                    userId,
                    Math.negateExact(totalPrice)
            ));
        }
        try {
            orderService.order(new OrderProcessDto(
                    storeInventoryDeductionList,
                    userAmountDeductionList,
                    orderList
            ));
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
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
        try {
            cartService.insertCartList(cartList);
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
            return;
        }
        InsertStatusPrinter.printInsertCartList(true);
    }

}
