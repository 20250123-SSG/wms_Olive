package com.minisec.user.view.printer;

import com.minisec.user.common.OrderStatus;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.helper.OrderWrapper;

import java.util.List;
import java.util.StringJoiner;

public class OrderDetailsPrinter {
    /**
     * -----------주문서-----------
     * (상품아이디). 샴푸 - 30개 | 5,000원
     *
     * 총 주문 수량 : 31개
     * 총 주문 가격 : 15,000원
     * 구매번호 : 1234
     * 구매 가맹점 : A가맹점
     * 주문 메모 : ㅓㄷㅈ래ㅔㅑㅓㄷ잘
     * 주문일 : 2025-01-03
     * 주문 상태 : 결제완료
     */
    private final static String PRODUCT_DETAILS = "%d) %s - %,d | %,d원\n";

    public static void printOne(OrderDto order) {
        List<OrderProductDto> orderProductList = order.getOrderProducts();

        System.out.println("========== 주문서 ===========");
        System.out.print(getOrderProductDetails(orderProductList));
        System.out.println("==========");
        System.out.printf("총 주문 수량 : %,d개\n", order.getTotalQuantity());
        System.out.printf("총 주문 가격 : %,d원\n", order.getTotalPrice());
        System.out.printf("구매번호 : %s\n", order.getOrderId() != 0  ? String.valueOf(order.getOrderId()) : "-");
        System.out.printf("구매 가맹점 : %s\n", order.getStore().getStoreName());
        System.out.printf("주문 메모 : %s\n",order.getOrderMemo() != null ? order.getOrderMemo() : "-");
        System.out.printf("주문일 : %s\n",order.getOrderDate() != null ? order.getOrderDate() : "-");
        System.out.printf("주문 상태 : %s\n", OrderStatus.getDesc(order.getOrderStatus()));
        System.out.println("==============================\n");
    }

    public static void printList(List<OrderDto> orders) {
        System.out.println("\n\n\n============= 구매 내역 =============\n");

        for (OrderDto order : orders) {
            printOne(order);
        }
        System.out.println("==================================\n\n\n");
    }

    private static String getOrderProductDetails(List<OrderProductDto> orderProducts) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i<orderProducts.size(); i++) {
            OrderProductDto orderProduct = orderProducts.get(i);

            result.append(
                    String.format(PRODUCT_DETAILS,
                            i+1,
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
