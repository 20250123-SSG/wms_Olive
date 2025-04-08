package com.minisec.store.controller.order;

import com.minisec.store.service.order.StoreOrderService;
import com.minisec.store.view.StoreOrderView;

import java.util.Scanner;

public class StoreOrderController {
    private Scanner sc = new Scanner(System.in);
    private final StoreOrderView     sov = new StoreOrderView();
    private final StoreOrderService  sos = new StoreOrderService();

    public void orderView(int manageId) {
        while (true) {
            System.out.println("1. 발주 주문");
            System.out.println("2. 발주 조회");
            System.out.println("3. 발주 수정");
            System.out.println("0. 돌아가기");

            System.out.print(">> 입력: ");
            int num = sc.nextInt();
            sc.nextLine();

            switch (num) {
                case 1:
                    sov.insertOrderView(manageId, sos);
                    break;
                case 2:
                    sov.selectOrderView(manageId,sos);
                    break;
                case 3:
                    sov.updateOrderView(manageId, sos);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
