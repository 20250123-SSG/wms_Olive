package com.minisec.user.model.dto.order;

public record StoreInventoryDeductionDto(int storeDetailId
                                       , int userOrderQuantity
) {
}
