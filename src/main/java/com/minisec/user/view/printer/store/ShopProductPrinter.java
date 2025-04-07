package com.minisec.user.view.printer.store;

import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.StoreDto;

import java.util.List;
import java.util.Map;

public class ShopProductPrinter {

    public static void printProductListByCategory(StoreDto store,
                                                  Map<String, List<StoreProductDto>> storeProductByCategory) {
        System.out.printf("========== %s 가맹점 상품 ==========\n", store.getStoreName());

        if (storeProductByCategory == null || storeProductByCategory.isEmpty()) {
            System.out.println("판매하는 상품이존재하지 않습니다.");
            return;
        }

        int uniqueNumber = 1;
        for (Map.Entry<String, List<StoreProductDto>> entry : storeProductByCategory.entrySet()) {
            String categoryName = entry.getKey();
            List<StoreProductDto> productList = entry.getValue();

            System.out.printf("< %s >\n", categoryName);
            for (StoreProductDto productDto : productList) {

                System.out.printf("\t%d. %s %,d원 - %,d개\n"
                        , uniqueNumber
                        , productDto.getProductName()
                        , productDto.getStoreProductPriceAfterDiscount()
                        , productDto.getStoreProductQuantity()
                );
                uniqueNumber++;
            }
            System.out.println();
            System.out.println("----------------------");
        }
    }

    public static void printProductDetail(StoreProductDto product) {
        System.out.println("========== 상품 디테일 ==========");
        System.out.printf("이름 : %s\n", product.getProductName());
        System.out.printf("가격 : %,d\n", product.getStoreProductPriceAfterDiscount());
        System.out.printf("수량 : %,d\n", product.getStoreProductQuantity());
        System.out.printf("카테고리 : %s\n", product.getCategory());
        System.out.printf("브랜드 : %s\n", product.getBrandName());
        System.out.printf("상품 설명 : %s\n", product.getProductDescription());
        System.out.printf("할인여부 : %s\n", product.getDiscount() > 0 ? "O" : "X");
        System.out.println("==========");
    }

}
