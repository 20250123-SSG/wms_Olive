package com.minisec.user.model.dto.cart;

import com.minisec.user.model.dto.order.OrderProcessDto;

public record CartOrderProcessDto(OrderProcessDto orderProcessDto,
                                  CartProductDeleteDto productDeleteList
) {
}
