package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.cart.CartDetailByStoreDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.cart.CartOrderProcessDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;
import com.minisec.user.model.dto.order.*;
import com.minisec.user.model.dto.store.StoreDto;
import com.minisec.user.model.dto.store.StoreInventoryDeductionDto;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;
import com.minisec.user.service.CartService;
import com.minisec.user.service.OrderService;
import com.minisec.user.view.printer.DeleteStatusPrinter;
import com.minisec.user.view.printer.ExceptionPrinter;
import com.minisec.user.view.printer.InsertStatusPrinter;
import com.minisec.user.view.printer.UpdateStatusPrinter;
import com.minisec.user.view.printer.order.OrderDetailsPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartController {

    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();

    public CartController() {
    }



    public Map<StoreDto, List<OrderProductDto>> selectAllCartDetailListByUserId(Login user) {
        Map<StoreDto, List<OrderProductDto>> result = new HashMap<>();

        List<CartDetailByStoreDto> cartDetailByStoreList = cartService.selectAllCartDetailListByUserId(user.getUserId());

        for (CartDetailByStoreDto cartDetailByStore : cartDetailByStoreList) {
            StoreDto storeDto = cartDetailByStore.getStore();
            List<OrderProductDto> orderProductList = cartDetailByStore.getOrderProductList();

            result.put(storeDto, orderProductList);
        }
        return result;
    }


    public void orderFromCart(Login user, List<OrderDto> orderList) {
        List<StoreInventoryDeductionDto> storeInventoryDeductionList = new ArrayList<>();
        List<UserBalanceUpdateDto> userAmountDeductionList = new ArrayList<>();
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

        try {
            orderService.orderFromCart(new CartOrderProcessDto(
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


    public void updateCartProductQuantity(Map<StoreDto, List<OrderProductDto>> allCartList,
                                          String inputUpdateCartProductQuantity,
                                          String inputEditQuantity) {

        List<OrderProductDto> allCartProduct = new ArrayList<>();
        for (StoreDto store : allCartList.keySet()) {
            allCartProduct.addAll(allCartList.get(store));
        }

        int index = 0;
        int editQuantity = 0;
        OrderProductDto editQuantityProduct = null;
        try {
            index = Integer.parseInt(inputUpdateCartProductQuantity.trim()) - 1;
            editQuantity = Integer.parseInt(inputEditQuantity.trim());

            if (editQuantity <= 0) {
                throw new IllegalArgumentException("수량은 1개 이상 입력해주세요.");
            }
            editQuantityProduct = allCartProduct.get(index);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수정 번호와 수량은 숫자로 입력해주세요.");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("존재하지 않는 상품 번호입니다");
        }

        editQuantityProduct.setQuantity(editQuantity);

        CartDto editCart = new CartDto();
        editCart.setCartId(editQuantityProduct.getDetailId());
        editCart.setOrderProduct(editQuantityProduct);

        try {
            cartService.updateCartProductQuantity(editCart);
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
            return;
        }
        UpdateStatusPrinter.printUpdateCartQuantity(true);
    }


    public void deleteCartListByChoice(Login user,
                                       Map<StoreDto, List<OrderProductDto>> allCartList,
                                       String inputDeleteCartNumber) {

        List<Integer> storeProductIdList = new ArrayList<>();

        List<OrderProductDto> allCartProduct = new ArrayList<>();
        for (StoreDto store : allCartList.keySet()) {
            allCartProduct.addAll(allCartList.get(store));
        }
        try {
            for (String input : inputDeleteCartNumber.split(",")) {
                int index = Integer.parseInt(input.trim()) - 1;
                OrderProductDto cartProduct = allCartProduct.get(index);

                storeProductIdList.add(cartProduct.getProduct().getStoreProductId());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("장바구니 번호를 입력해주세요.");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("존재하지 않는 장바구니 상품입니다.");
        }

        try {
            cartService.deleteCartList(new CartProductDeleteDto(
                    user.getUserId(),
                    storeProductIdList
            ));
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
            return;
        }
        DeleteStatusPrinter.printDeleteCart(true);
    }


    public void deleteAllCartListByUserId(Login user) {
        try {
            cartService.deleteAllCartListByUserId(user.getUserId());
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
            return;
        }
        DeleteStatusPrinter.printDeleteCart(true);
    }

}
