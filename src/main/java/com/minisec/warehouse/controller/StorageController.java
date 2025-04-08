package com.minisec.warehouse.controller;

import com.minisec.warehouse.model.dto.StorageDto;
import com.minisec.warehouse.service.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StorageController {

    private StorageService storageService = new StorageService();

    // 입고 내역 조회
    public void selectFilteredStorageList(int manageId) {
        List<StorageDto> list = storageService.selectAllStorage(manageId);
        List<StorageDto> finalReceivedList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n입고 대기 상품 목록");
        System.out.println("─────────────────────────────────────────────────────────");
        for (StorageDto storage : list) {
            String productName = storageService.getProductNameById(storage.getProductId());
            System.out.println("▶ " + productName + " / " + storage.getSupplierName() + " "  + " / " + storage.getStorageQuantity() + "개");
        }
        System.out.println("─────────────────────────────────────────────────────────\n");

        System.out.println("\n입고 진행");
        System.out.println("────────────────────────────────────────────────────────────────────────────────");
        for (StorageDto storage : list) {
            // 제품명 가져오기
            String productName = storageService.getProductNameById(storage.getProductId());

            System.out.println("\n입고 내역: " + productName + " / " + storage.getSupplierName() + " 공급 / " + storage.getStorageQuantity() + "개");

            // 사용자 확인 요청
            System.out.print("이 제품을 입고하시겠습니까? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (!input.equals("Y")) {
                System.out.println("입고가 취소되었습니다.");
                continue;
            }

            // 불량품 검사
            for (int i = 0; i <= 100; i += 10) {
                System.out.print("\r불량품 검사중: " + i + "%");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            int finalQuantity = storageService.calculateFinalStorageQuantity(storage);

            System.out.println("\n불량품 검사 완료! 최종 입고 수량: " + finalQuantity + "개\n");

            // db 업데이트(warehouse_detail)
            storageService.insertOrUpdateWarehouseProduct(storage.getWarehouseId(), storage.getProductId(), finalQuantity);

            System.out.println("입고 완료되었습니다.\n");

            // 로그 기록
            storage.setStorageQuantity(finalQuantity);
            storageService.insertWarehouseProductLog(storage);

            // 최종 입고된 리스트에 추가
            StorageDto receivedStorage = new StorageDto(
                    storage.getStorageId(),
                    storage.getWarehouseId(),
                    storage.getProductId(),
                    storage.getSupplierName(),
                    finalQuantity,
                    storage.getCreatedAt()
            );

            finalReceivedList.add(receivedStorage);
        }

        // 최종 입고된 상품 목록 출력
        System.out.println("\n최종 입고된 상품 목록");
        System.out.println("──────────────────────────────────────────────────────────────────────────");
        System.out.printf("%-5s %-35s %-15s %-10s\n", "번호", "상품명(product_id)", "제조사", "입고 수량(불량품 제외)");
        System.out.println("──────────────────────────────────────────────────────────────────────────");

        if (finalReceivedList.isEmpty()) {
            System.out.println("최종 입고된 상품이 없습니다.");
        } else {
            int index = 1;
            for (StorageDto storage : finalReceivedList) {
                String productName = storageService.getProductNameById(storage.getProductId());
                System.out.printf("%-5d %-25s(%d) %-20s %-10d\n", index++, productName, storage.getProductId(), storage.getSupplierName(), storage.getStorageQuantity());
            }
        }
        System.out.println("──────────────────────────────────────────────────────────────────────────");
    }
}
