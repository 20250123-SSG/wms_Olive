package com.minisec.user.view.printer.order;

import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.order.OrderDto;

import java.util.List;

public class OrderSimplePrinter {

    public static void print(List<OrderDto> orderList) {
        System.out.println("=============== 누적 주문 기록 확인 ===============");

        for (OrderDto order : orderList) {
            printByOne(order);
            System.out.println();
            System.out.println();
        }
    }


    public static void printByOne(OrderDto order) {
        System.out.printf("주문번호 : %s\n",    order.getOrderId() != 0 ? String.valueOf(order.getOrderId()) : "-");
        System.out.printf("구매 가맹점 : %s\n", order.getStore().getStoreName());
        System.out.printf("주문 메모 : %s\n",   order.getOrderMemo() != null ? order.getOrderMemo() : "-");
        System.out.printf("주문일 : %s\n",      order.getOrderDate() != null ? order.getOrderDate() : "-");
        System.out.printf("주문 상태 : %s\n",   OrderStatus.getDesc(order.getOrderStatus()));
    }

}
