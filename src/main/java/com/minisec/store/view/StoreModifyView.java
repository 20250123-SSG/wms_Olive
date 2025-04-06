package com.minisec.store.view;


import com.minisec.store.controller.StoreModifyController;
import com.minisec.store.controller.StoreSelectController;

import java.util.Map;
import java.util.Scanner;

public class StoreModifyView {
    private Scanner sc = new Scanner(System.in);
    private StoreModifyController storeModifyController = new StoreModifyController();
    private StoreSelectController storeSelectController = new StoreSelectController();

    public void insertStoreProductDetail(int manageId) {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("  ~~~ 상품 전체 목록 ~~~ ");
            storeModifyController.productList(manageId);

            // 상품등록
            System.out.print("\n등록할 상품 번호 선택: ");
            String productId = sc.nextLine();

            System.out.print("\n가맹점 상품 가격 입력: ");
            String storeDetailPrice = sc.nextLine();

            Map<String, String> requestParam = Map.of(
                    "productId", productId,
                    "storeDetailPrice", storeDetailPrice
            );

            storeModifyController.insertStoreProduct(requestParam, manageId);
            storeModifyController.selectStoreProductById(productId, manageId);
            System.out.print("\n계속 등록하시겠습니까? (Y/N): ");
            if(sc.nextLine().toUpperCase().equals("N")){
                break;
            }
        }
    }

    public void updateStoreProductDetail(int manageId) {
        storeSelectController.selectProductListAll(manageId);
        System.out.print("\n수정할 정보를 입력해주세요.");
        System.out.print("\n상품 번호: ");
        String productId = sc.nextLine();
        System.out.print("상품 가격: ");
        String productPrice = sc.nextLine();
        System.out.print("상품 할인 여부(0/1): ");
        String isDiscount = sc.nextLine();
        System.out.print("상품 판매 여부(0/1): ");
        String productStatus = sc.nextLine();

        Map<String, String> requestParam = Map.of(
                "productId", productId,
                "storeDetailPrice", productPrice,
                "isDiscount", isDiscount,
                "storeDetailStatus", productStatus
        );

        storeModifyController.updateStoreProduct(requestParam, manageId);
    }


    public void deleteStoreProductDetail(int manageId) {
        storeSelectController.selectProductListAll(manageId);
        System.out.print("\n삭제할 정보를 입력해주세요.");
        System.out.print("\n상품 번호: ");
        String productId = sc.nextLine();


        Map<String, String> requestParam = Map.of(
                "productId", productId
        );
        storeModifyController.deleteStoreProduct(requestParam, manageId);
    }


}

