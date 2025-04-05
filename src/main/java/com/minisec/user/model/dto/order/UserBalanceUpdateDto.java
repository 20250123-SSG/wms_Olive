package com.minisec.user.model.dto.order;


public record UserBalanceUpdateDto(int userId,
                                   int userOrderAmountForDeducted
) {
}