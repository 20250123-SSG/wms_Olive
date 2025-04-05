package com.minisec.store.view;

import com.minisec.common.login.Login;

import java.util.Scanner;


public class StoreView {
    private Scanner sc = new Scanner(System.in);
    private StoreSelectView storeSelectView = new StoreSelectView();
    private StoreProductView storeProductView = new StoreProductView();
    StoreOrderView sov = new StoreOrderView();

    public void viewStore(Login loginInfo, int manageId) {

        while (true) {
            System.out.print("""
                    \n======================================
                    ----- 가맹점 관리자 ------
                    1. 가맹점 재고 발주
                    2. 가맹점 상품 관리
                    3. 고객 주문 확인
                    0. 돌아가기
                    ========================================
                    >> 입력: """);

            String storeMenu = sc.nextLine();
            switch (storeMenu) {

                case "1":
                    sov.orderView();
                    break;
                case "2":
                    storeProductView.storeProductDetail(manageId);
                    break;
//                case "3": storeCheckUserOrder(); break;
                case "0":
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }
}
