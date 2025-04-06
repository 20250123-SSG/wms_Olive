package com.minisec.store.dao;

import com.minisec.store.dto.StoreProductDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreProductMapper {

    List<StoreProductDto> selectAllProduct(@Param("manageId") int manageId);
    List<StoreProductDto> selectNameSearch(@Param("manageId") int manageId, @Param("searchName") String searchName);
    List<StoreProductDto> selectCategorySearch(@Param("manageId") int manageId, @Param("searchCategory") String searchCategory);
    List<StoreProductDto> selectProductLowStock(@Param("manageId") int manageId);
}
