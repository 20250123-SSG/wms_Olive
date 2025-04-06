package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.StorageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageMapper {
    // 전체 입고 내역 조회
    List<StorageDto> selectAllStorage();

    // 입고 수량 db 업데이트
    void updateStorageQuantity(@Param("storageId") int storageId, @Param("newQuantity") int newQuantity);
    
    // 입고 로그 기록
    void insertWarehouseReceiveLog(StorageDto storage);
}