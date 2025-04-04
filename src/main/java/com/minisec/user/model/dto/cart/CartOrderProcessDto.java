package com.minisec.user.model.dto.cart;

import com.minisec.user.model.dto.order.OrderProcessDto;
import com.minisec.user.model.dto.order.OrderProductDto;

import java.util.List;

public record CartOrderProcessDto(OrderProcessDto orderProcessDto,
                                  List<Integer> cartIdList) {
}
