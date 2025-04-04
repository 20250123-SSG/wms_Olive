package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.ProductDto;

import java.util.List;

public interface ProductMapper {
    // 전체 상품 조회
    List<ProductDto> getProductList();
}
