package com.minisec.warehouse.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShipmentDetailDto {
    private int productId;
    private String productName;
    private int storeOrderDetailQuantity;
    private LocalDateTime createdAt;
}
