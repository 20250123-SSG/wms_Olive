package com.minisec.store.view;

import com.minisec.store.dto.StoreProductDto;

import java.util.List;

public class StoreDetailView {

    // 가맹점 재고 조회된 상품 목록을 출력해주는 화면
    public static void displayDetailList(List<StoreProductDto> list) {
        if (list.isEmpty()) {
            System.out.println("조회된 상품이 없습니다.");
        } else {
            System.out.println("\n─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n");
            System.out.println("가맹점 상품 목록\n");
            for (StoreProductDto store : list) {
                System.out.printf("번호-%d   상품명: %s,  브랜드: %s,  카테고리: %s,  가격: %d원,  수량: %d개,  할인: %s,  상태: %s\n",
                        store.getProductId(),
                        store.getProductName(),
                        store.getProductBrandName(),
                        store.getCategoryName(),
                        store.getStoreDetailPrice(),
                        store.getStoreDetailQuantity(),
                        store.getIsDiscount() == 1 ? "Y" : "N",
                        store.getStoreDetailStatus() == 1 ? "Y" : "N"
                );
            }
            System.out.println("\n─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n");
        }
    }

    // 가맹점에 없는 상품 목록 출력해주는 화면
    public static void displayProductList(List<StoreProductDto> list) {
        System.out.println("\n────────────────────────────────────────────────────────────────────────────────\n");
        System.out.println("추가 가능한 상품 목록\n");
        for (StoreProductDto store : list) {
                System.out.printf("번호-%d   상품명: %s,  브랜드: %s,  카테고리: %s \n",
                        store.getProductId(),
                        store.getProductName(),
                        store.getProductBrandName(),
                        store.getCategoryName());
            }
        System.out.println("\n────────────────────────────────────────────────────────────────────────────────\n");
    }

    // 등록, 수정, 삭제 요청시 결과를 출력해주는 화면
    public static void displayModifyResult(String type, int result){ // type:"메뉴 등록"|"메뉴 수정"|"메뉴 삭제" / result:최종결과
        System.out.println( type + (result > 0 ? "에 성공했습니다." : "에 실패했습니다. 다시 확인해주세요") );
    }

}
