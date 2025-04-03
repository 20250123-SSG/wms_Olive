package com.minisec.warehouse.controller;

import com.minisec.warehouse.model.dto.ProductDto;
import com.minisec.warehouse.service.ProductService;

import java.util.List;

public class ProductController {
    private ProductService productService = new ProductService();

    // 전체 상품 목록 조회
    public void selectAllProducts() {
        List<ProductDto> list = productService.selectAllProducts();
        for (ProductDto product : list) {
            System.out.println(product);
        }
    }
}
