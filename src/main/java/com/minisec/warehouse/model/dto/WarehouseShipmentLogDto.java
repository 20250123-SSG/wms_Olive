package com.minisec.warehouse.model.dto;

import com.minisec.common.product.ProductDto;
import com.minisec.store.dto.StoreDto;
import com.minisec.store.dto.StoreOrderDto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WarehouseShipmentLogDto {
    private int warehouseId;
    private int productId;
    private int storeOrderId;
    private int warehouseShippingQuantity;
    private LocalDateTime warehouseLogTime;

    private ProductDto product;
    private StoreOrderDto storeOrder;
    private StoreDto store;
}
