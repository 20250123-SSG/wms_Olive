package com.minisec.warehouse.controller;

import com.minisec.warehouse.model.dto.ProductDto;
import com.minisec.warehouse.service.ProductService;

import java.util.List;

public class ProductController {
    private ProductService productService = new ProductService();

    // 창고 내 전체 상품 조회
    public void selectAllProducts() {
        System.out.println("\n📦 창고 내 전체 상품 조회 📦\n");
        List<ProductDto> list = productService.selectAllProducts();

        System.out.println("────────────────────────────────────────────────────────────────────────────────");
        System.out.printf("%-5s | %-5s | %-10s | %-10s | %-20s%n",
                "ID", "창고", "상품 ID", "수량", "등록일");
        System.out.println("────────────────────────────────────────────────────────────────────────────────");

        for (ProductDto product : list) {
            System.out.printf("%-5d | %-7d | %-12d | %-12d | %-20s%n",
                    product.getWarehouseDetailId(),
                    product.getWarehouseId(),
                    product.getProductId(),
                    product.getWarehouseDetailQuantity(),
                    product.getCreatedAt());
        }
        System.out.println("────────────────────────────────────────────────────────────────────────────────");
    }
}
