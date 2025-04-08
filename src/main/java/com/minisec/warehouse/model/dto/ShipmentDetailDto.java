package com.minisec.warehouse.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShipmentDetailDto {
    private int storeOrderId;
    private int productId;
    private int storeOrderDetailQuantity;
}
