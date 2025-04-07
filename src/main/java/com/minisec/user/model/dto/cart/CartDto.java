package com.minisec.user.model.dto.cart;

import com.minisec.user.model.dto.OrderProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartDto {

    private Integer cartId;
    private int storeId;
    private String storeName;
    private int userId;

    private OrderProductDto orderProduct;


    public CartDto(int storeId, int userId, OrderProductDto orderProduct) {
        this.storeId = storeId;
        this.userId = userId;
        this.orderProduct = orderProduct;
    }

}
