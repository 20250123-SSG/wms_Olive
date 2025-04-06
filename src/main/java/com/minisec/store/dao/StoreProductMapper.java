package com.minisec.store.dao;

import com.minisec.store.dto.StoreProductDto;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

import java.util.List;

public interface StoreProductMapper {

    List<StoreProductDto> selectAllProduct(Map<String, Object> param);
    List<StoreProductDto> selectNameSearch(Map<String, Object> param);
    List<StoreProductDto> selectCategorySearch(Map<String, Object> param);
    List<StoreProductDto> selectProductLowStock(Map<String, Object> param);;
}
