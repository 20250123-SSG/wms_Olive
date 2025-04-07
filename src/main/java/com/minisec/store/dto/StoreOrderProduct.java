package com.minisec.store.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderProduct {
    private int productId;
    private String productName;
    private String productBrandName;
    private String productDescription;
}
