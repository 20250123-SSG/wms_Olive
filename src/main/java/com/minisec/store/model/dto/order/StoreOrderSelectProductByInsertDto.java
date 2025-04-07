package com.minisec.store.model.dto.order;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrderSelectProductByInsertDto {
    private int productId;
    private String productName;
    private String productBrandName;
    private String productDescription;
}
