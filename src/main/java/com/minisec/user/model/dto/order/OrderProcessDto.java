package com.minisec.user.model.dto.order;

import com.minisec.user.model.dto.store.StoreInventoryDeductionDto;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;

import java.util.List;

public record OrderProcessDto(List<StoreInventoryDeductionDto> storeInventoryDeductionList,
                              List<UserBalanceUpdateDto> userBalanceUpdateList,
                              List<OrderDto> orderDtoList
) {
}
