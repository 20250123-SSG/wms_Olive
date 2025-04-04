package com.minisec.store.view;


import com.minisec.store.controller.StoreModifyController;

import java.util.Map;
import java.util.Scanner;

public class StoreModifyView {
    private Scanner sc = new Scanner(System.in);
    private StoreModifyController storeModifyController = new StoreModifyController();

    public void insertStoreProductDetail(int manageId) {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("  ~~~ 상품 전체 목록 ~~~ ");

            storeModifyController.productList();
            System.out.print("\n등록할 상품 번호 선택: ");
            String productId = sc.nextLine();
            //if(productId){
            //    System.out.println("잘못 입력하셨습니다. 정확한 상품 번호를 입력해주세요.");
            //}
            System.out.print("\n가맹점 상품 가격 입력: ");
            String storeDetailPrice = sc.nextLine();

            Map<String, String> requestParam = Map.of(
                    "productId", productId,
                    "storeDetailPrice", storeDetailPrice
            );

            // 상품 등록
            storeModifyController.insertStoreProduct(requestParam);
            System.out.println("등록 성공");
            storeModifyController.selectStoreProductById(productId);
            System.out.println("\n계속 등록하시겠습니까? (Y/N): ");
            if(sc.nextLine().toUpperCase().equals("N")){
                break;
            }
        }
    }
}

// 계속 등록하시겠습니까? 와 같은 반복 추가필요!
// 수정에서 재고는 건들이나? 그치 임의로 수정할수도 있지

