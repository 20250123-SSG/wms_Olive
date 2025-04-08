package com.minisec.warehouse.view;

import com.minisec.warehouse.model.dto.ShipmentDto;
import com.minisec.warehouse.model.dto.WarehouseReceiveLogDto;
import com.minisec.warehouse.model.dto.WarehouseShipmentLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WarehouseResultView {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void displayProductList(List<WarehouseProductDetailDto> list) {
        System.out.printf("%-5s %-20s %-10s %-20s%n", "번호", "상품이름", "수량", "마지막수정일");

        for (int i = 0; i < list.size(); i++) {
            WarehouseProductDetailDto item = list.get(i);

            System.out.printf("%-5d %-20s %-10d %-20s%n"
                    ,
                    i + 1,
                    item.getProduct().getProductName(),
                    item.getWarehouseDetailQuantity(),
                    dtf.format(item.getProduct().getModifiedAt()));
        }
    }

    public static void displayProductShippingLogList(List<WarehouseShipmentLogDto> list) {
        System.out.printf("%-5s %-10s %-10s %-10s %-20s%n", "번호", "주문가맹점", "주문제목", "출고수량", "마지막수정일");

        for (int i = 0; i < list.size(); i++) {
            WarehouseShipmentLogDto item = list.get(i);
            System.out.printf("%-5s %-10s %-10s %-10s %-20s%n"
                    ,
                    i + 1,
                    item.getStore().getStoreName(),
                    item.getStoreOrder().getStoreOrderSubject(),
                    item.getWarehouseShippingQuantity(),
                    item.getWarehouseLogTime());
        }

    }

    public static void displayProductReceiveLogList(List<WarehouseReceiveLogDto> list) {
        System.out.printf("%-5s %-10s %-10s %-10s %n", "번호", "보급처", "입고수량", "입고일" );

        for (int i = 0; i < list.size(); i++) {
            WarehouseReceiveLogDto item = list.get(i);
            System.out.printf("%-5s %-10s %-10s %-10s %n"
                    ,
                    i + 1,
                    item.getSupplierName(),
                    item.getWarehouseReceiveQuantity(),
                    item.getWarehouseLogTime());
        };

    }

    public static void displayShipmentList(List<ShipmentDto> orders) {
        System.out.println("\n────────────────────────────────────────────────────────────────────────────────");
        System.out.println("주문번호\t 주문명\t\t\t 주문메모\t\t 주문상태\t 주문발생일");
        for (int i = 0; i < orders.size(); ++i) {
            ShipmentDto order = orders.get(i);
            System.out.printf("%d\t %s\t\t\t %s\t\t %s\t\t %s \n",
                    i + 1, order.getStoreOrderSubject(), order.getStoreOrderMemo(), getOrderStatus(order.getStoreOrderStatus()), order.getCreatedAt());
        }
    }

    public static String getOrderStatus(char status) {
        String orderStatus = "";
        if (status == '1') {
            orderStatus = "대기";
        } else if (status == '2') {
            orderStatus = "수주";
        } else if (status == '3') {
            orderStatus = "거절";
        } else {
            orderStatus = "완료";
        }

        return orderStatus;
    }
}
