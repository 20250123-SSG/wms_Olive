package com.minisec.warehouse.model.dto;

import com.minisec.common.product.ProductDto;
import com.minisec.store.dto.StoreOrderDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WarehouseLogDto {
    private int productId;
    private int storeOrderId;
    private char warehouseLogType;
    private int warehouseLogQuantity;
    private LocalDateTime warehouseLogTime;

    private ProductDto product;
    private StoreOrderDto storeOrder;
}
