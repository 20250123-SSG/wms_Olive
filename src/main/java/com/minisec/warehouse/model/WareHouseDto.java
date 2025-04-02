package com.minisec.warehouse.model;

public class WareHouseDto {
    private int productId;
    private int categoryId;
    private String productName;
    private String productBrand;
    private int productPrice;
    private String productDescription;

    // 기본 생성자 (MyBatis에서 필요)
    public WareHouseDto() {}

    public WareHouseDto(int productId, int categoryId, String productName, String productBrand, int productPrice, String productDescription) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    public int getProductId() { return productId; }
    public int getCategoryId() { return categoryId; }
    public String getProductName() { return productName; }
    public String getProductBrand() { return productBrand; }
    public int getProductPrice() { return productPrice; }
    public String getProductDescription() { return productDescription; }

    @Override
    public String toString() {
        return productId + " | " + productName + " (" + productBrand + ") - " + productPrice + "원\n설명: " + productDescription;
    }
}
