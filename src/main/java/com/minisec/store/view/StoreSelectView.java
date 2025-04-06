package com.minisec.store.view;

import com.minisec.store.controller.StoreSelectController;

import java.util.Scanner;

public class StoreSelectView {
    private StoreSelectController storeSelectController = new StoreSelectController();
    private Scanner sc = new Scanner(System.in);

    public void listStoreProductDetail(int manageId) {
        while (true) {
            System.out.print("""
                    \n======================================
                    ----- 가맹점 상품 조회 ------
                    1. 전체 조회
                    2. 상품명 조회 (키워드 가능)
                    3. 카테고리명 조회 (키워드 가능)
                    4. 재고 10개 이하 상품만 조회
                    0. 돌아가기
                    ======================================
                    >> 입력: """);

            String productDetailView = sc.nextLine();
            switch (productDetailView) {
                case "1":
                    storeSelectController.selectProductListAll(manageId);
                    break;
                case "2":
                    storeSelectController.selectProductListName(manageId, inputName("상품명"));
                    break;
                case "3":
                    storeSelectController.selectProductListCategory(manageId, inputName("카테고리명"));
                    break;
                case "4":
                    storeSelectController.selectProductListLow(manageId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }

        }
    }

    // 상품명, 카테고리명 키워드 입력 받는 서브 화면
    public String inputName(String type) { // type:"상품명"|"카테고리명"
        System.out.printf(">> %s 입력: ", type);
        return sc.nextLine();
    }

}

