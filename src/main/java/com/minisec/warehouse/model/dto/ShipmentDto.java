package com.minisec.warehouse.model.dto;

import java.util.List;
import java.time.LocalDateTime;
import lombok.*;

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

    private List<ShipmentDetailDto> shipmentDetails;
    private List<ProductDto> products;
}
