package com.minisec.store.dao;

import com.minisec.store.dto.StoreProductDto;

import java.util.List;

public interface StoreModifyMapper {

    List<StoreProductDto> productList();
    int insertStoreProduct(StoreProductDto storeProduct);
    List<StoreProductDto> selectStoreProductById();

}

