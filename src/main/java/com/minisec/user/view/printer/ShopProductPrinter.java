package com.minisec.user.view.printer;

import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.StoreDto;

import java.util.List;

public class ShopProductPrinter {

    public static void printProductList(StoreDto store, List<StoreProductDto> productList) {
        System.out.printf("========== %s 가맹점 상품 ==========\n", store.getStoreName());

        if (productList == null || productList.isEmpty()) {
            System.out.println("판매하는 상품이존재하지 않습니다.");
            return;
        }

        for (int i = 0; i < productList.size(); i++) {
            System.out.printf("%d. %s %,d원 - %,d개\n"
                    , i + 1
                    , productList.get(i).getProductName()
                    , productList.get(i).getStoreProductPriceAfterDiscount()
                    , productList.get(i).getStoreProductQuantity()
            );
        }
        System.out.println("==========");
    }

    public static void printProductDetail(StoreProductDto product) {
        System.out.println("========== 상품 디테일 ==========");
        System.out.printf("이름 : %s\n",      product.getProductName());
        System.out.printf("가격 : %,d\n",     product.getStoreProductPriceAfterDiscount());
        System.out.printf("수량 : %,d\n",     product.getStoreProductQuantity());
        System.out.printf("카테고리 : %s\n",  product.getCategory());
        System.out.printf("브랜드 : %s\n",    product.getBrandName());
        System.out.printf("상품 설명 : %s\n", product.getProductDescription());
        System.out.printf("할인여부 : %s\n",  product.getDiscount() > 0 ? "O" : "X");
        System.out.println("==========");
    }

}
