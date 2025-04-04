package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.WarehouseDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehouseMapper {
    // 전체 상품 조회
    List<WarehouseDto> getProductList();

    // 입고 수량 db 업데이트
    void updateStorageQuantity(@Param("storageId") int storageId, @Param("newQuantity") int newQuantity);
}

