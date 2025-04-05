package com.minisec.user.model.dto.order;

import com.minisec.user.model.dto.StoreProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderProductDto {

    private int detailId;
    private int quantity;
    private int totalPrice;

    private StoreProductDto product;

    public OrderProductDto(StoreProductDto product, int quantity, int totalPrice) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public void updateQuantity(int quantity) {
        if ((this.quantity + quantity) > product.getStoreProductQuantity()) { //todo 예외

        }
        this.quantity += quantity;
        calculateTotalPrice(product);
    }

    private void calculateTotalPrice(StoreProductDto product) {
        this.totalPrice = product.getStoreProductPriceAfterDiscount() * quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductDto that = (OrderProductDto) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }

}
