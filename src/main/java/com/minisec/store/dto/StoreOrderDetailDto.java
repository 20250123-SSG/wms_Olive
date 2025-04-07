package com.minisec.store.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderDetailDto {
    private int storeOrderDetailId;
    private int storeOrderId;
    private int productId;
    private int storeOrderDetailQuantity;
    private LocalDateTime modifiedAt;
}
