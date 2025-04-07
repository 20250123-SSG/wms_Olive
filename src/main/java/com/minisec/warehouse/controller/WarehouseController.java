package com.minisec.warehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.minisec.warehouse.model.dto.WarehouseReceiveLogDto;
import com.minisec.warehouse.model.dto.WarehouseShipmentLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;
import com.minisec.warehouse.service.WarehouseProductService;
import com.minisec.warehouse.view.WarehouseResultView;

public class WarehouseController {

    private final WarehouseProductService warehouseService = new WarehouseProductService();
    private final Scanner sc = new Scanner(System.in);

    // 창고 내 전체 상품 조회
    public Map<Integer, Integer> selectAllProducts(int warehouseId) {
        System.out.println("\n📦 창고 내 전체 상품 조회 📦");
        List<WarehouseProductDetailDto> list = warehouseService.selectAllProducts(warehouseId);
        WarehouseResultView.displayProductList(list);
        // <출력번호:상품번호> map을 생성후 return
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            WarehouseProductDetailDto item = list.get(i);
            map.put(i + 1, item.getProduct().getProductId());
        }
        return map;
    }

    // 상품 입출고 로그 조회
    public void selectSearchProductLog(int searchProductId) {
        System.out.println("1. 입고내역 조회하기");
        System.out.println("2. 출고내역 조회하기");
        int logType = sc.nextInt();
        if (logType == 1) {
            List<WarehouseReceiveLogDto> list = warehouseService.selectSearchReceiveProductLog(searchProductId);
            WarehouseResultView.displayProductReceiveLogList(list);

        } else if (logType == 2) {
            List<WarehouseShipmentLogDto> list = warehouseService.selectSearchShippingProductLog(searchProductId);
            // 출고
            WarehouseResultView.displayProductShippingLogList(list);

        } else{
            System.out.println("잘못된 입력값입니다.");
        }
    }
}
