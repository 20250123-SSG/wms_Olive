package com.minisec.user.model.dto.cart;

import com.minisec.user.model.dto.order.OrderProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartDto {

    private int cartId;
    private int storeId;
    private int userId;
    private OrderProductDto orderProduct; /// 수량 가격

    public CartDto(int storeId, int userId, OrderProductDto orderProduct) {
        this.storeId = storeId;
        this.userId = userId;
        this.orderProduct = orderProduct;
    }



}
