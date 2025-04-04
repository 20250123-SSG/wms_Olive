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

    private int orderId; //티데일아이디여야하는데
    private StoreProductDto product;
    private int quantity;
    private int totalPrice;

    public OrderProductDto(StoreProductDto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public void updateQuantity(int quantity) {
        if ((this.quantity + quantity) > product.getStoreProductQuantity()) { //todo 예외

        }
        this.quantity += quantity;
    }

    public void calculateTotalPrice() {
        this.totalPrice = product.getProductPriceAfterDiscount() * quantity;
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
