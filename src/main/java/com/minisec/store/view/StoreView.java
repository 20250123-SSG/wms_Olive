package com.minisec.store.view;

import com.minisec.common.login.Login;
import com.minisec.store.controller.order.StoreOrderController;
import com.minisec.store.controller.user.StoreUserController;

import java.util.Scanner;


public class StoreView {

    private Scanner sc = new Scanner(System.in);
    private StoreProductView storeProductView = new StoreProductView();
    StoreOrderController soc = new StoreOrderController();
    StoreUserController  suc = new StoreUserController();

    public void viewStore(Login loginInfo,int manageId) {
        while (true) {
            System.out.print("""
                    \n======================================
                                 
                                  가맹점관리 
                    
                    1. 가맹점 재고 발주
                    2. 가맹점 상품 관리
                    3. 고객 주문 확인
                    0. 로그아웃
                    
                    ======================================
                    
                    > 입력: """);

            String storeMenu = sc.nextLine();
            switch (storeMenu) {
                case "1":
                    soc.orderView(manageId);
                    break;
                case "2":
                    storeProductView.storeProductDetail(manageId);
                    break;
                case "3":
                    suc.userView(manageId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }
}
