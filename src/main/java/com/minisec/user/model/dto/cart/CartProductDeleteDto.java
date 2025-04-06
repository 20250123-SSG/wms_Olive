package com.minisec.user.model.dto.cart;

import java.util.List;

public record CartProductDeleteDto(int userId,
                                   List<Integer> storeProductIdList
) {
}
