package com.minisec.user.view.details;

import com.minisec.common.login.Login;
import com.minisec.user.controller.CartController;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.StoreDto;

import com.minisec.user.model.manager.LocalCartOrderManager;
import com.minisec.user.view.printer.ExceptionPrinter;
import com.minisec.user.view.printer.cart.CartDetailsPrinter;

import java.util.*;

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

        while (true) {
            System.out.println("""
                    1. 모두 구매
                    2. 선택 구매
                    0. 뒤로가기
                    """);

            int functionNum = InputFunctionNumberView.input();
            if (functionNum == 0) break;
            if (functionNum == 1) {
                orderAllFromCart(user);
                return;
            }
            if (functionNum == 2) {
                choiceOrderProduct(user);
                return;
            }
            ExceptionPrinter.print("존재하지 않는 기능입니다.");
        }
    }



    private void orderAllFromCart(Login user) {
        List<OrderDto> orderList = localCartOrderManager.getOrderListWhenAllFromCart(user);
        orderList = new InputOrderMemoView().run(orderList);

        cartController.orderFromCart(user, orderList);
    }


    private void choiceOrderProduct(Login user) {
        while (true) {
            StoreDto storeDto = inputStore();
            List<Integer> orderProductIndex = inputOrderProductNum();
            if (orderProductIndex.isEmpty()) return;

            try {
                localCartOrderManager.addOrder(storeDto, orderProductIndex);
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
                continue;
            }

            CartDetailsPrinter.print(
                    localCartOrderManager.getLocalUserCartList(),
                    localCartOrderManager.getOrderListByStore()
            );

            while (true) {
                System.out.println("""
                        1. 구매 확정하기
                        2. 구매 목록에 상품 담기
                        0. 취소하기
                        >> 입력:""");
                int functionNum = InputFunctionNumberView.input();

                if (functionNum == 0) return;
                if (functionNum == 2) break;
                if (functionNum == 1) {
                    orderByChoiceFromCart(user);
                    return;
                }
            }
        }
    }

    private void orderByChoiceFromCart(Login user) {
        List<OrderDto> orderList = localCartOrderManager.getOrderListWhenChoice(user);
        orderList = new InputOrderMemoView().run(orderList);

        cartController.orderFromCart(user, orderList);
    }


    private StoreDto inputStore() {
        while (true) {
            System.out.println("[ 구매할 가맹점을 입력해주세요. ]");
            System.out.print(">>입력:");

            try {
                return localCartOrderManager.getStoreByStoreName(sc.nextLine());
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private List<Integer> inputOrderProductNum() {
        while (true) {
            System.out.println("[ 구매할 상품의 장바구니 번호를 모두 입력하세요. ex)1,2,3,4  (0.뒤로가기) ] ");
            String productNum = sc.nextLine().trim();

            if ("0".equals(productNum)) {
                return new ArrayList<>();
            }
            try {
                return localCartOrderManager.getOrderProductIndexList(productNum);
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private boolean readUserCartList(Login user) {
        localCartOrderManager = new LocalCartOrderManager(
                cartController.selectAllCartDetailListByUserId(user)
        );
        if (localCartOrderManager.isCartEmpty()) {
            System.out.println("\n\n장바구니가 비어있습니다.");
            return false;
        }
        return true;
    }

}
