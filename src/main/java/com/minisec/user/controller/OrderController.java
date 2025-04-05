package com.minisec.user.controller;

import com.minisec.common.login.Login;
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

    public void selectAllOrderDetailListByUserId() {

    }

    private void selectAllOrderDetailListByOrderId(List<OrderDto> orderList) {
        List<OrderDetailFilterDto> orderDetailFilterList = new ArrayList<>();
        for (OrderDto orderDto : orderList) {
            orderDetailFilterList.add(OrderDetailFilterDto.builder()
                    .orderId(orderDto.getOrderId())
                    .build());
        }
        for (OrderDetailFilterDto orderDetailFilterDto : orderDetailFilterList) {
            orderList = orderService.selectAllOrderDetailListByOrderId(orderDetailFilterDto);
            OrderDetailsPrinter.printList(orderList);
        }
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
        int insertResult = orderService.order(new OrderProcessDto(
                storeInventoryDeductionList,
                userAmountDeductionList,
                orderList
        ));
        if (insertResult == 0) {
            InsertStatusPrinter.printInsertOrderList(false);
            return;
        }

        InsertStatusPrinter.printInsertOrderList(true);
        selectAllOrderDetailListByOrderId(orderList);
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
        int insertResult = cartService.insertCartList(cartList);
        InsertStatusPrinter.printInsertCartList(insertResult == cartList.size());
    }

}
