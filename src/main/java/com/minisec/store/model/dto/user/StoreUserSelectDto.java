package com.minisec.store.model.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreUserSelectDto {
    private int userOrderId;
    private int userId;
    private String storeName;
    private String productName;
    private String productBrandName;
    private String categoryName;
    private int userOrderDetailQuantity;
    private String userOrderStatus;
    private String userOrderMemo;
}
