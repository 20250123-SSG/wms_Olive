package com.minisec.user.model.dto.cart;

import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.store.StoreDto;
import lombok.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartDetailByStoreDto {

    private int cartId;
    private int userId;

    private StoreDto store;
    private List<OrderProductDto> orderProductList;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartDetailByStoreDto that = (CartDetailByStoreDto) o;
        return store == that.store && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(store, userId);
    }

}
