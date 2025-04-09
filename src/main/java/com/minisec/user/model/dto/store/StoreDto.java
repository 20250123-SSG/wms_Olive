package com.minisec.user.model.dto.store;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StoreDto {

    private int storeId;
    private String storeName;
    private String phone;
    private String address;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StoreDto storeDto = (StoreDto) o;
        return storeId == storeDto.storeId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storeId);
    }

}
