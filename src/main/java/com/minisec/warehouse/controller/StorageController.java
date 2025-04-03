package com.minisec.warehouse.controller;

import com.minisec.warehouse.model.dto.StorageDto;
import com.minisec.warehouse.service.StorageService;

import java.util.List;

public class StorageController {

    private StorageService storageService = new StorageService();

    public void selectFilteredStorageList() {
        List<StorageDto> list = storageService.selectAllStorage();
        System.out.println("\n📦 입고 내역 조회 📦");
        for (StorageDto storage : list) {
            int finalQuantity = storageService.calculateFinalStorageQuantity(storage);
            System.out.println("\n🔍 불량품 검사중...");
            System.out.println(finalQuantity);
        }
    }
}
