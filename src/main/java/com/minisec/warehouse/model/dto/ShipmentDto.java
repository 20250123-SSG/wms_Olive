package com.minisec.warehouse.model.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShipmentDto {
    private int storeOrderId;
    private String storeOrderSubject;
    private char storeOrderStatus;
    private String storeOrderMemo;
    private LocalDateTime createdAt;
    private List<ShipmentDetailDto> orderDetails;
}
