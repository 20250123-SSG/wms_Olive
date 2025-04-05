package com.minisec.user.model.dto;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StoreProductDto {

    private int storeProductId;
    private int productId;

    private String productName;
    private int storeProductPrice;
    private int storeProductQuantity;

    private String category;
    private String brandName;
    private int discount;
    private String productDescription;



    public int getProductPriceAfterDiscount(){
        if(discount != 0){
            return (int) (storeProductPrice * (1 - (discount / 100.0)));
        }
        return storeProductPrice;
    }

    public void deleteStoreProductQuantity(int quantity){
        storeProductQuantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StoreProductDto that = (StoreProductDto) o;
        return storeProductId == that.storeProductId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storeProductId);
    }
}
