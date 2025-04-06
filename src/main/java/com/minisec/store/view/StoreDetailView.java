package com.minisec.store.view;

import com.minisec.store.dto.StoreProductDto;

import java.util.List;

public class StoreDetailView {

    // 가맹점 재고 조회된 상품 목록을 출력해주는 화면
    public static void displayDetailList(List<StoreProductDto> list) {
        if(list.isEmpty()){
            System.out.println("조회된 상품이 없습니다.");
        }else {
            for (StoreProductDto store : list) {
                System.out.printf( "%d\t%s\t%s\t%s\t%d원\t%d\t%d\t%d\n",
                        store.getProductId(),
                        store.getProductName(),
                        store.getProductBrandName(),
                        store.getCategoryName(),
                        store.getStoreDetailPrice(),
                        store.getStoreDetailQuantity(),
                        store.getIsDiscount(),
                        store.getStoreDetailStatus() );
            }
        }
    }


    // 총 상품 목록 조회를 출력해주는 화면
    public static void displayProductList(List<StoreProductDto> list) {
            for (StoreProductDto store : list) {
                System.out.printf( "%d\t%s\t%s\t%s\t%d원\n",
                        store.getProductId(),
                        store.getProductName(),
                        store.getProductBrandName(),
                        store.getCategoryName(),
                        store.getProductPrice() );
            }
    }


    // 등록, 수정, 삭제 요청시 결과를 출력해주는 화면
    public static void displayModifyResult(String type, int result){ // type:"메뉴 등록"|"메뉴 수정"|"메뉴 삭제" / result:최종결과
        System.out.println( type + (result > 0 ? "에 성공했습니다." : "에 실패했습니다. 다시 확인해주세요") );
    }
}
