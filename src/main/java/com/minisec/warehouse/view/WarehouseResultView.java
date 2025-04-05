package com.minisec.warehouse.view;

import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WarehouseResultView {
    public static void displayProductList(List<WarehouseProductDetailDto> list) {
        System.out.printf("%-5s %-20s %-10s %-20s%n", "번호", "상품이름", "수량", "마지막수정일");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < list.size(); i++) {
            WarehouseProductDetailDto item = list.get(i);

            System.out.printf("%-5d %-20s %-10d %-20s%n"
                    ,
                    i+1,
                    item.getProduct().getProductName(),
                    item.getWarehouseDetailQuantity(),
                    dtf.format(item.getProduct().getModifiedAt()));
        }
    }
}
