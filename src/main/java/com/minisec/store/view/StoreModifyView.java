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
            storeModifyController.productList(manageId);

            // 상품등록
            System.out.print("\n> 등록할 상품 번호: ");
            String productId = sc.nextLine();

            System.out.print("\n> 가맹점 상품 가격: ");
            String storeDetailPrice = sc.nextLine();

            System.out.print("\n> 가맹점 상품 재고: ");
            String storeDetailQuantity = sc.nextLine();

            Map<String, String> requestParam = Map.of(
                    "productId", productId,
                    "storeDetailPrice", storeDetailPrice,
                    "storeDetailQuantity", storeDetailQuantity
            );


            // 상품 등록
            storeModifyController.insertStoreProduct(requestParam, manageId);
            System.out.print("\n> 계속 등록하시겠습니까? (Y/N): ");
            if (sc.nextLine().equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    public void updateStoreProductDetail(int manageId) {
        storeSelectController.selectProductListAll(manageId);
        System.out.print("\n수정할 정보를 입력해주세요.");
        System.out.print("\n> 상품 번호: ");
        String productId = sc.nextLine();

        System.out.print("> 상품 가격: ");
        String productPrice = sc.nextLine();

        String isDiscountInput;
        while (true) {
            System.out.print("> 상품 할인 여부(Y/N): ");
            isDiscountInput = sc.nextLine().toLowerCase();
            if (isDiscountInput.equals("y") || isDiscountInput.equals("n")) {
                break;
            } else {
                System.out.println("잘못 입력하셨습니다. Y 또는 N으로 입력해주세요.");
            }
        }
        String isDiscount = isDiscountInput.equals("y") ? "1" : "0";

        String productStatusInput;
        while (true) {
            System.out.print("> 상품 판매 여부(Y/N): ");
            productStatusInput = sc.nextLine().toLowerCase();
            if (productStatusInput.equals("y") || productStatusInput.equals("n")) {
                break;
            } else {
                System.out.println("잘못 입력하셨습니다. Y 또는 N으로 입력해주세요.");
            }
        }
        String productStatus = productStatusInput.equals("y") ? "1" : "0";

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
        System.out.print("\n> 상품 번호: ");
        String productId = sc.nextLine();


        Map<String, String> requestParam = Map.of(
                "productId", productId
        );
        storeModifyController.deleteStoreProduct(requestParam, manageId);
    }


}

