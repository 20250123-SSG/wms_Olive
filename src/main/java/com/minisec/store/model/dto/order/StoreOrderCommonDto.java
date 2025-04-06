package com.minisec.store.model.dto.order;



import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderCommonDto {
    private int storeOrderId;
    private int warehouseId;
    private int storeId;
    private String storeOrderSubject;
    private String storeOrderStatus;
    private String storeOrderMemo;
    private LocalDateTime shipmentDate;
    private LocalDateTime modifiedAt;
}
