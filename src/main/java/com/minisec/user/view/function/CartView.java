package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.CartController;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.view.details.CartOrderView;
import com.minisec.user.view.printer.cart.CartDetailsPrinter;

import java.util.*;

public class CartView {
    private final Scanner sc = new Scanner(System.in);
    private final CartController cartController = new CartController();

    public void run(Login user) {
       while (true) {
           System.out.println("""
                1. 장바구니 조회
                2. 장바구니 물품 구매
                3. 장바구니 삭제
                4. 장바구니 비우기
                0. 뒤로가기
                >> 입력:""");

           switch (Integer.parseInt(sc.nextLine())) {
               case 0: return;
               case 1: selectCartListByUserId(user); break;
               case 2: new CartOrderView().run(user); break;
               case 3 : deleteCartListByChoice(user); break;
               case 4: deleteAllCartListByUserId(user); break;
           }
       }
    }

    private void selectCartListByUserId(Login user) {
        CartDetailsPrinter.printUniqueNumber(cartController.selectAllCartDetailListByUserId(user));
    }

    public void deleteAllCartListByUserId(Login user) {
        cartController.deleteAllCartListByUserId(user);
    }




    public void deleteCartListByChoice(Login user) {
        Map<StoreDto, List<OrderProductDto>> allCartList = cartController.selectAllCartDetailListByUserId(user);
        CartDetailsPrinter.printUniqueNumber(allCartList);

        System.out.println("[ 삭제하고 싶은 장바구니 번호를 입력해주세요. ex)1,2,3,4 ]");
        String inputDeleteCartNumber = sc.nextLine();

        cartController.deleteCartListByChoice(user, allCartList, inputDeleteCartNumber);
    }

}
