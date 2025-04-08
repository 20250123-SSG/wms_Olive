package com.minisec.warehouse.model.dto;

import lombok.*;
import java.time.LocalDateTime;

import com.minisec.common.product.ProductDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShipmentDto {
    private int storeOrderId;
    private int warehouseId;
    private int storeId;
    private String storeOrderSubject;
    private char storeOrderStatus;
    private String storeOrderMemo;
    private LocalDateTime createdAt;
    private LocalDateTime shipmentDate;

    private ShipmentDetailDto shipmentDetail;
    private ProductDto product;
}
