package com.minisec.warehouse.controller;

import com.minisec.warehouse.mapper.WareHouseMapper;
import com.minisec.warehouse.model.WareHouseDto;

import java.util.List;

public class WareHouseController {
    private WareHouseMapper mapper;

    public WareHouseController() {
        mapper = new WareHouseMapper();
    }

    public void selectProductList() {
        List<WareHouseDto> products = mapper.getProductList();
        System.out.println("\n📦 전체 상품 목록 📦");
        for (WareHouseDto product : products) {
            System.out.println(product);
        }
    }
}
