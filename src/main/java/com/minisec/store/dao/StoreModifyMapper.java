package com.minisec.store.dao;

import com.minisec.store.dto.StoreProductDto;

import java.util.List;
import java.util.Map;

public interface StoreModifyMapper {

    List<StoreProductDto> unregisteredProductList(int storeId);
    int insertStoreProduct(Map<String, Object> param);
    List<StoreProductDto> selectStoreProductById(Map<String, String> param);
    int updateStoreProduct(Map<String, Object> param);
    int deleteStoreProduct(Map<String, Object> param);
}

