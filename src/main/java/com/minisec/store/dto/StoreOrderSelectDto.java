package com.minisec.store.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderSelectDto {
    private int storeId;
    private String storeName;
    private String productName;
    private String categoryName;
    private String productBrandName;
    private String storeOrderSubject;
    private String storeOrderStatus;
    private int storeOrderDetailQuantity;
}
