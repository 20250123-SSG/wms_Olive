package com.minisec.user.model.dto.order;

/// 충전시 사용
public record UserBalanceUpdateDto(int userId,
                                   int userOrderAmountForDeducted
) {
}