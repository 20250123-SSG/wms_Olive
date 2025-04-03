package com.minisec.warehouse.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private int warehouseDetailId;
    private int warehouseId;
    private int productId;
    private int warehouseDetailQuantity;
    private String createdAt;
    private String modifiedAt;
}
