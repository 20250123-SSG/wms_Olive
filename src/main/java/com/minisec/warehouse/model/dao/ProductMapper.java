package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.ProductDto;
import java.util.List;

public interface ProductMapper {
    List<ProductDto> getProductList();
}
