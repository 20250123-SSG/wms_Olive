package com.minisec.user.model.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private int orderId;
    //private int storeId;
    private StoreDto store;
    private int userId;

    private String orderStatus;
    private String orderDate;

    private int totalQuantity;
    private int totalPrice;

    @Setter
    private String orderMemo;

    private List<OrderProductDto> orderProducts;


    public OrderDto(StoreDto store, int userId, String orderStatus, int totalQuantity, int totalPrice, List<OrderProductDto> orderProducts) {
        this.store = store;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.orderProducts = orderProducts;
    }

}
