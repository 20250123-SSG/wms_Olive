package com.minisec.warehouse.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private int productId;
    private int categoryId;
    private String productName;
    private String productBrandName;
    private int productPrice;
    private String productDescription;
}
