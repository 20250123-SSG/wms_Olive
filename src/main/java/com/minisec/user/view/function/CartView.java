package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.CartController;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.store.StoreDto;
import com.minisec.user.view.details.CartOrderView;
import com.minisec.user.view.details.InputFunctionNumberView;
import com.minisec.user.view.printer.ExceptionPrinter;
import com.minisec.user.view.printer.cart.CartDetailsPrinter;

import java.util.*;

public class CartView {
    private final Scanner sc = new Scanner(System.in);

    private final CartController cartController = new CartController();

    public void run(Login user) {

        while (true) {
            System.out.println("\n\n=========================================================");
            System.out.println("--------------------- 장바구니 관리 ---------------------\n");
            System.out.println("""
                     1. 장바구니 조회
                     2. 장바구니 물품 구매
                     3. 장바구니 상품 수량 수정
                     4. 장바구니 삭제
                     5. 장바구니 비우기
                     0. 뒤로가기
                    """);

            switch (InputFunctionNumberView.input()) {
                case 0:                                  return;
                case 1: selectCartListByUserId(user);    break;
                case 2: orderFormCart(user);             break;
                case 3: updateCartProductQuantity(user); break;
                case 4: deleteCartListByChoice(user);    break;
                case 5: deleteAllCartListByUserId(user); break;
                default:
                    System.out.println("존재하지 않는 기능입니다.");
                    continue;
           }
       }
    }



    private void selectCartListByUserId(Login user) {
        CartDetailsPrinter.printUniqueNumber(cartController.selectAllCartDetailListByUserId(user));
    }


    private void orderFormCart(Login user) {
        new CartOrderView().run(user);
    }


    private void updateCartProductQuantity(Login user) {
        Map<StoreDto, List<OrderProductDto>> allCartList = cartController.selectAllCartDetailListByUserId(user);
        if (allCartList.isEmpty()) {
            System.out.println("\n\n장바구니가 비어있습니다.");
            return;
        }
        CartDetailsPrinter.printUniqueNumber(allCartList);

        while (true) {
            System.out.println("[ 수량을 수정하고 싶은 장바구니 번호를 입력해주세요. (0.뒤로가기) ]");
            String inputUpdateCartProductQuantity = sc.nextLine();
            if (("0".equals(inputUpdateCartProductQuantity))) return;
            System.out.println("[ 수정할 수량을 입력해주세요. ]");
            String inputEditQuantity = sc.nextLine();

            try {
                cartController.updateCartProductQuantity(
                        allCartList,
                        inputUpdateCartProductQuantity,
                        inputEditQuantity
                );
                return;
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private void deleteCartListByChoice(Login user) {
        Map<StoreDto, List<OrderProductDto>> allCartList = cartController.selectAllCartDetailListByUserId(user);
        if (allCartList.isEmpty()) {
            System.out.println("\n\n장바구니가 비어있습니다.");
            return;
        }
        CartDetailsPrinter.printUniqueNumber(allCartList);

        while (true) {
            try {
                System.out.println("[ 삭제하고 싶은 장바구니 번호를 모두 입력해주세요. ex)1,2,3,4 ]");
                String inputDeleteCartNumber = sc.nextLine();

                cartController.deleteCartListByChoice(user, allCartList, inputDeleteCartNumber);
                return;
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private void deleteAllCartListByUserId(Login user) {
        cartController.deleteAllCartListByUserId(user);
    }

}
