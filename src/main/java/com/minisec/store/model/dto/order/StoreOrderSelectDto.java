package com.minisec.store.model.dto.order;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderSelectDto {
    private String storeName;
    private String productName;
    private String categoryName;
    private String productBrandName;
    private String storeOrderSubject;
    private String storeOrderStatus;
    private int storeOrderDetailQuantity;
}
