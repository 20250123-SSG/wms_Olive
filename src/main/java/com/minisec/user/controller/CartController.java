package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.cart.CartDetailByStoreDto;
import com.minisec.user.model.dto.cart.CartOrderProcessDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;
import com.minisec.user.model.dto.order.*;
import com.minisec.user.service.CartService;
import com.minisec.user.service.OrderService;
import com.minisec.user.view.printer.DeleteStatusPrinter;
import com.minisec.user.view.printer.InsertStatusPrinter;
import com.minisec.user.view.printer.order.OrderDetailsPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartController {

    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();

    public Map<StoreDto,List<OrderProductDto>> selectAllCartDetailListByUserId(Login user) {
        Map<StoreDto, List<OrderProductDto>> result = new HashMap<>();

        List<CartDetailByStoreDto> cartDetailByStoreList = cartService.selectAllCartDetailListByUserId(user.getUserId());

        for(CartDetailByStoreDto cartDetailByStoreDto : cartDetailByStoreList) {

            StoreDto storeDto = cartDetailByStoreDto.getStore();
            List<OrderProductDto> orderProductList = new ArrayList<>();

            for(OrderProductDto orderProduct : cartDetailByStoreDto.getOrderProductList()) {
                if(orderProductList.contains(orderProduct)) {
                    int newQuantity = orderProduct.getQuantity();
                    orderProduct = orderProductList.get(orderProductList.indexOf(orderProduct));
                    orderProduct.updateQuantity(newQuantity);
                    continue;
                }
                orderProductList.add(orderProduct);
            }
            result.put(storeDto,orderProductList);
        }
        return result;
    }


    public void orderFromCart(Login user, List<OrderDto> orderList){
        List<StoreInventoryDeductionDto> storeInventoryDeductionList = new ArrayList<>(); ///1
        List<UserBalanceUpdateDto> userAmountDeductionList = new ArrayList<>(); ///2
        List<Integer> storeProductIdList = new ArrayList<>();

        for (OrderDto orderDto : orderList) {
            for (OrderProductDto orderProduct : orderDto.getOrderProducts()) {

                int storeDetailId = orderProduct.getProduct().getStoreProductId();
                int userOrderQuantity = orderProduct.getQuantity();
                storeInventoryDeductionList.add(new StoreInventoryDeductionDto(
                        storeDetailId,
                        userOrderQuantity
                ));

                storeProductIdList.add(orderProduct.getProduct().getStoreProductId());
            }
            int userId = orderDto.getUserId();
            int totalPrice = orderDto.getTotalPrice();
            userAmountDeductionList.add(new UserBalanceUpdateDto(
                    userId,
                    Math.negateExact(totalPrice)
            ));
        }

        int orderResult = orderService.orderFromCart(new CartOrderProcessDto(
                new OrderProcessDto(
                        storeInventoryDeductionList,
                        userAmountDeductionList,
                        orderList
                ),
                new CartProductDeleteDto(
                        user.getUserId(),
                        storeProductIdList
                )
        ));
        if (orderResult == 0) {
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


    public void deleteAllCartListByUserId(Login user) {
        int deleteResult = cartService.deleteAllCartListByUserId(user.getUserId());
        if (deleteResult == 0) {
            DeleteStatusPrinter.printDeleteCart(false);
        }
        DeleteStatusPrinter.printDeleteCart(true);
    }


    public void deleteCartListByChoice(Login user,
                                       Map<StoreDto,List<OrderProductDto>> allCartList,
                                       String inputDeleteCartNumber){
        List<Integer> storeProductIdList = new ArrayList<>();

        List<OrderProductDto> allCartProduct = new ArrayList<>();
        for(StoreDto storeDto : allCartList.keySet()) {
            allCartProduct.addAll(allCartList.get(storeDto));
        }
        for(String input : inputDeleteCartNumber.split(",")) {
            int index = Integer.parseInt(input.trim())-1;
            OrderProductDto cartProduct = allCartProduct.get(index);

            storeProductIdList.add(cartProduct.getProduct().getStoreProductId());
        }

        int deleteResult = cartService.deleteCartList(new CartProductDeleteDto(
                user.getUserId(),
                storeProductIdList
        ));
        if (deleteResult == 0) {
            DeleteStatusPrinter.printDeleteCart(false);
        }
        DeleteStatusPrinter.printDeleteCart(true);
    }

}
