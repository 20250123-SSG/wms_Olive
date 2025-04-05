package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.OrderController;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.view.printer.order.OrderSimplePrinter;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.List;
import java.util.Scanner;


public class OrderHistoryView {
    private Scanner sc = new Scanner(System.in);

    private final OrderController orderController = new OrderController();

    public void run(Login user) {
        System.out.println("""
                1. 누적 구매 내역 조회
                2. 취소 구매 내역 조회
                0. 뒤로가기
                >> 입력:""");

        switch (Integer.parseInt(sc.nextLine())) {
            case 0:                                  return;
            case 1: selectOrderList(user);           break;
            case 2: selectCanceledStatusOrder(user); break;
            default: return;
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
            orderController.selectOneOrderDetailByOrderId(inputOrderId, simpleOrderList);
        }
    }

    private void selectCanceledStatusOrder(Login user) {
        orderController.selectCanceledStatusOrder(user);
    }

}
