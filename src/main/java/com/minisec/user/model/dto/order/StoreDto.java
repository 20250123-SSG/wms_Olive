package com.minisec.user.model.dto.order;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StoreDto {

    private int storeId;
    private String storeName;
    private String phone;
    private String address;

}
