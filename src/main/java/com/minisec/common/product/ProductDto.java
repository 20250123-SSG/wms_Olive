package com.minisec.common.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private int productId;
    private int categoryId;
    private String productName;
    private String productBrandName;
    private int productPrice;
    private String productDescription;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}