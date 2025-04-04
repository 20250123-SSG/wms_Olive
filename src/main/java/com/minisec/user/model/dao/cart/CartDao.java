package com.minisec.user.model.dao.cart;

import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.order.OrderDto;

import java.util.List;

public interface CartDao {

    int insertCartList(List<CartDto> cartList);

}
