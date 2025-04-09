package com.minisec.store.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreDto {
    private int storeId;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}