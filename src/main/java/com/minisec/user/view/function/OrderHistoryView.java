package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.OrderController;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.view.details.InputFunctionNumberView;
import com.minisec.user.view.printer.ExceptionPrinter;
import com.minisec.user.view.printer.order.OrderSimplePrinter;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.sun.source.tree.TryTree;

import java.util.List;
import java.util.Scanner;


public class OrderHistoryView {
    private Scanner sc = new Scanner(System.in);

    private final OrderController orderController = new OrderController();

    public void run(Login user) {

        while (true) {
            System.out.println("\n\n===========================================================");
            System.out.println("--------------------- 주문 내역 관리 ----------------------\n");
            System.out.println("""
                    1. 누적 구매 내역 조회
                    2. 취소 구매 내역 조회
                    0. 뒤로가기
                    """);

           switch (InputFunctionNumberView.input()) {
               case 0:                                  return;
               case 1: selectOrderList(user);           break;
               case 2: selectCanceledStatusOrder(user); break;
               default:
                   System.out.println("존재하지 않는 기능입니다.");
                   continue;
           }
       }
    }



    private void selectOrderList(Login user) {
        List<OrderDto> simpleOrderList = orderController.selectAllOrderListByUserId(user);
        if (simpleOrderList == null || simpleOrderList.isEmpty()) {
            System.out.println("주문 내역이 없습니다.");
            return;
        }
        OrderSimplePrinter.print(simpleOrderList);

        while (true) {
            System.out.println("상세확인하고 싶은 주문 번호를 입력해주세요. (0. 뒤로가기)");
            String inputOrderId = sc.nextLine();
            if ("0".equals(inputOrderId)) {
                return;
            }

            try {
                orderController.selectOneOrderDetailByOrderId(inputOrderId, simpleOrderList);
            } catch (IllegalArgumentException e) {
                ExceptionPrinter.print(e.getMessage());
            }
        }
    }


    private void selectCanceledStatusOrder(Login user) {
        orderController.selectCanceledStatusOrder(user);
    }

}
