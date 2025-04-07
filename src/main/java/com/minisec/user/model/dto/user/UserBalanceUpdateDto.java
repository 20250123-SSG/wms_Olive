package com.minisec.user.model.dto.user;


public record UserBalanceUpdateDto(int userId,
                                   int userOrderAmountForDeducted
) {
}