package com.minisec.store.model.dto.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderCommonDetailDto {
    private int storeOrderDetailId;
    private int storeOrderId;
    private int productId;
    private int storeOrderDetailQuantity;
    private LocalDateTime modifiedAt;
}
