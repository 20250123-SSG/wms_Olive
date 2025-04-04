package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.CartController;
import com.minisec.user.view.details.CartOrderView;

import java.util.Scanner;

public class CartView {
    private final Scanner sc = new Scanner(System.in);
    private final CartController cartController = new CartController();

    public void run(Login user) {
        System.out.println("""
                1. 장바구니 조회
                2. 장바구니 물품 구매
                3. 장바구니 삭제
                4. 장바구니 비우기
                0. 뒤로가기
                >> 입력:""");

        switch (Integer.parseInt(sc.nextLine())) {
            case 0:
                return;
            case 2:
                new CartOrderView().run(user);
                break;
        }
    }

}
