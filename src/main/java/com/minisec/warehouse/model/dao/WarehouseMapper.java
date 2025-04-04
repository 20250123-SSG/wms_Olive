package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.WarehouseDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehouseMapper {
    // 전체 상품 조회
    List<WarehouseDto> getProductList();
}

