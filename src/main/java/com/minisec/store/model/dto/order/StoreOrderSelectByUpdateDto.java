package com.minisec.store.model.dto.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderSelectByUpdateDto {
    private int storeOrderDetailId;
    private String storeOrderSubject;
    private String storeOrderMemo;
    private String productName;
    private LocalDateTime modifiedAt;
    private int productId;
    private int storeOrderDetailQuantity;
}
