package com.minisec.user.view.details;

import com.minisec.common.login.Login;
import com.minisec.user.controller.CartController;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;

import com.minisec.user.model.manager.LocalCartOrderManager;
import com.minisec.user.model.manager.helper.OrderDtoAssembler;
import com.minisec.user.model.manager.helper.OrderWrapper;
import com.minisec.user.view.printer.cart.CartDetailsPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class CartOrderView {
    private final Scanner sc = new Scanner(System.in);

    private final CartController cartController = new CartController();
    private LocalCartOrderManager localCartOrderManager;


    public void run(Login user) {

        if (!readUserCartList(user)) return;

        CartDetailsPrinter.print(
                localCartOrderManager.getLocalUserCartList(),
                localCartOrderManager.getOrderListByStore()
        );
        System.out.println("""
                1. 모두 구매
                2. 선택 구매
                0. 뒤로가기
                >> 입력:""");

        switch (Integer.parseInt(sc.nextLine())) {
            case 0:                         return;
            case 1: orderAllFromCart(user); break;
            case 2: orderByChoice(user);    break;
        }
    }


    public void orderByChoice(Login user) {
        while (true) {
            StoreDto storeDto = inputStore();
            List<Integer> orderProductIndex = inputOrderProductNum();
            if(orderProductIndex.isEmpty()) return;

            localCartOrderManager.addOrder(storeDto, orderProductIndex);

            CartDetailsPrinter.print(
                    localCartOrderManager.getLocalUserCartList(),
                    localCartOrderManager.getOrderListByStore()
            );
            System.out.println("""
                    1. 구매 확정하기
                    2. 구매 목록에 상품 담기
                    0. 취소하기
                    >> 입력:""");
            int functionNum = Integer.parseInt(sc.nextLine());
            if (functionNum == 0) {
                return;
            }
            if (functionNum == 1) {
                break;
            }
        }

        List<OrderDto> orderList = localCartOrderManager.getOrderListWhenChoice(user);
        orderList = new InputOrderMemoView().run(orderList);

        cartController.orderFromCart(user, orderList);
    }


    public void orderAllFromCart(Login user) {
        List<OrderDto> orderList = localCartOrderManager.getOrderListWhenAllFromCart(user);
        orderList = new InputOrderMemoView().run(orderList);

        cartController.orderFromCart(user, orderList);
    }

    private List<Integer> inputOrderProductNum() {
        System.out.println("[ 구매할 상품의 장바구니 코드를 모두 입력하세요. ex)1,2,3,4  (0.뒤로가기) ] ");
        String productNum = sc.nextLine().trim();

        if ("0".equals(productNum)) {
            return new ArrayList<>();
        }
        return localCartOrderManager.getOrderProductIndexList(productNum);
    }

    private StoreDto inputStore() {
        System.out.println("[ 구매할 가맹점을 입력해주세요. ]");
        System.out.print(">>입력:");

        return localCartOrderManager.getStoreByStoreName(sc.nextLine());
    }

    private boolean readUserCartList(Login user) {
        localCartOrderManager = new LocalCartOrderManager(
                cartController.selectAllCartDetailListByUserId(user)
        );
        if (localCartOrderManager.isCartEmpty()) {
            System.out.println("[ 장바구니가 비어있습니다. ]");
            return false;
        }
        return true;
    }

}
