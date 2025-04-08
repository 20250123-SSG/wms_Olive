package com.minisec.store.controller.user;

import com.minisec.store.service.order.StoreOrderService;
import com.minisec.store.service.user.StoreUserService;
import com.minisec.store.view.UserOrderView;


import java.util.Scanner;

public class StoreUserController {
    private Scanner sc = new Scanner(System.in);
    private final UserOrderView uov = new UserOrderView();
    private final StoreUserService sus = new StoreUserService();

    public void userView(int storeId) {
        while (true) {
            System.out.println("1.전체 고객주문상품조회");
            System.out.println("2.고객주문승인");
            System.out.println("3.승인대기중인 고객주문 조회");
            System.out.println("0. 돌아가기");

            System.out.print(">> 입력: ");
            int num = sc.nextInt();
            sc.nextLine();

            switch (num) {
                case 1:
                    uov.selectOrderView(storeId,sus);
                    break;
                case 2:
                    uov.updateOrderStatusView(storeId,sus);
                    break;
                case 3:
                    uov.selectOrderByOrderStatusPendingView(storeId,sus);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
