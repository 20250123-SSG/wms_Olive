package com.minisec.warehouse.model.dto;

import com.minisec.common.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WarehouseProductDetailDto {
    private int warehouseDetailId;
    private int productId;
    private int warehouseId;
    private int warehouseDetailQuantity;
    private ProductDto product;
}