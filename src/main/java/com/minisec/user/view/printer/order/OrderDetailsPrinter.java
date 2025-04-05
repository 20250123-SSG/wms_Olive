package com.minisec.user.view.printer.order;

import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;

import java.util.List;

public class OrderDetailsPrinter {
    private final static String PRODUCT_DETAILS = "%d) %s - %,d | %,d원\n";

    public static void printOne(OrderDto order) {
        order.calculateTotalPriceAndQuantity();

        List<OrderProductDto> orderProductList = order.getOrderProducts();

        System.out.println("========== 주문서 ===========");
        System.out.print(getOrderProductDetails(orderProductList));
        System.out.println("==========");
        System.out.printf("총 주문 수량 : %,d개\n", order.getTotalQuantity());
        System.out.printf("총 주문 가격 : %,d원\n", order.getTotalPrice());
        System.out.printf("주문번호 : %s\n",        order.getOrderId() != 0 ? String.valueOf(order.getOrderId()) : "-");
        System.out.printf("구매 가맹점 : %s\n",     order.getStore().getStoreName());
        System.out.printf("주문 메모 : %s\n",       order.getOrderMemo() != null ? order.getOrderMemo() : "-");
        System.out.printf("주문일 : %s\n",          order.getOrderDate() != null ? order.getOrderDate() : "-");
        System.out.printf("주문 상태 : %s\n",       OrderStatus.getDesc(order.getOrderStatus()));
        System.out.println("==============================\n");
    }

    public static void printList(List<OrderDto> orders) {
        for (OrderDto order : orders) {
            printOne(order);
        }
    }

    private static String getOrderProductDetails(List<OrderProductDto> orderProducts) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < orderProducts.size(); i++) {
            OrderProductDto orderProduct = orderProducts.get(i);
            result.append(
                    String.format(PRODUCT_DETAILS,
                            i + 1,
                            orderProduct.getProduct().getProductName(),
                            orderProduct.getQuantity(),
                            orderProduct.getTotalPrice()
                    )
            );
        }
        result.append("\n");
        return result.toString();
    }

}
