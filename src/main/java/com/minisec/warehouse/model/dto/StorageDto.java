package com.minisec.warehouse.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StorageDto {
    private int storageId;
    private int warehouseId;
    private int productId;
    private String supplierName;
    private int storageQuantity;
    private String createdAt;
}
