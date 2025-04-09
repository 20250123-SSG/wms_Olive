package com.minisec.store.model.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreUserSelectDto {
    private String userName;
    private int userOrderDetailId;
    private int userOrderId;
    private String storeName;
    private String productName;
    private String productBrandName;
    private String categoryName;
    private int userOrderDetailQuantity;
    private String userOrderMemo;
    private String userOrderStatus;
}
