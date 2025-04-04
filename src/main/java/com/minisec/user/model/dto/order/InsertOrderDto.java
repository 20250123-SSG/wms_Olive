package com.minisec.user.model.dto.order;

import java.util.List;

public record InsertOrderDto(List<StoreInventoryDeductionDto> storeInventoryDeductionList
                           , List<UserBalanceUpdateDto> userBalanceUpdateList
                           , List<OrderDto> orderDtoList
) {
}
