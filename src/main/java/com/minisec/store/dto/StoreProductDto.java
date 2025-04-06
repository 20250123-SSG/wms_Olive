package com.minisec.store.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
        // (상품아이디, 상품명, 브랜드명, 상품카테고리명, 상품가격, 수량, 할인여부)
public class StoreProductDto {

    public int productId;
    public String productName;
    public String productBrandName;
    private int productPrice;
    public String categoryName;
    public int storeId;
    public int storeDetailId;
    public int storeDetailPrice;
    public int storeDetailQuantity;
    public int isDiscount;
    public int storeDetailStatus;
    private int manageId;


}
