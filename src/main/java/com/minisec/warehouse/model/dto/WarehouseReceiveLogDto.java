package com.minisec.warehouse.model.dto;

import com.minisec.common.product.ProductDto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WarehouseReceiveLogDto {
    private int warehouseReceiveLogId;
    private String supplierName;
    private int warehouseReceiveQuantity;
    private LocalDateTime warehouseLogTime;
    private ProductDto product;
}
