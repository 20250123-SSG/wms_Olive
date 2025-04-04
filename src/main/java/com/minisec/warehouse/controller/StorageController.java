package com.minisec.warehouse.controller;

import com.minisec.warehouse.model.dto.StorageDto;
import com.minisec.warehouse.service.StorageService;

import java.util.List;

public class StorageController {

    private StorageService storageService = new StorageService();

    // 입고 내역 조회
    public void selectFilteredStorageList() {
        List<StorageDto> list = storageService.selectAllStorage();
        System.out.println("\n입고 내역 조회");
        System.out.println("────────────────────────────────────────────────────────────────────────────────\n");
        for (StorageDto storage : list) {

            // 0%~100% 검사 진행
            for (int i = 0; i <= 100; i += 10) {
                System.out.print("\r불량품 검사중: " + i + "%");
                try {
                    Thread.sleep(200); // 0.2초 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            int finalQuantity = storageService.calculateFinalStorageQuantity(storage);

            // db 업데이트
            storageService.updateStorageQuantity(storage.getStorageId(), finalQuantity);

            String supplierName = (storage.getSupplierName() != null) ? storage.getSupplierName() : "알 수 없음";
            System.out.println("\n" + supplierName + " - 최종 입고 수량: " + finalQuantity + "개\n");        }
    }
}
