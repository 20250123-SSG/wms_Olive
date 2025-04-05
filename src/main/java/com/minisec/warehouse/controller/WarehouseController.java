package com.minisec.warehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minisec.warehouse.model.dto.WarehouseLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;
import com.minisec.warehouse.service.WarehouseProductService;
import com.minisec.warehouse.view.WarehouseResultView;

public class WarehouseController {

    private final WarehouseProductService warehouseService = new WarehouseProductService();

    // 창고 내 전체 상품 조회
    public Map<Integer, Integer> selectAllProducts(int warehouseId) {
        System.out.println("\n📦 창고 내 전체 상품 조회 📦");
        List<WarehouseProductDetailDto> list = warehouseService.selectAllProducts(warehouseId);
        WarehouseResultView.displayProductList(list);
        // <출력번호:상품번호> map을 생성후 return
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            WarehouseProductDetailDto item = list.get(i);
            map.put(i+1, item.getProduct().getProductId());
        }
        return map;
    }

    public void selectSearchProductLog(int searchProductId) {
        List<WarehouseLogDto> list = warehouseService.selectSearchProductLog(searchProductId);
    }
}
