package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.StorageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageMapper {
    // 전체 입고 내역 조회
    List<StorageDto> selectAllStorage();

    // warehouse_detail 테이블에서 product_id 존재 여부 확인
    Integer selectWarehouseProductQuantity(@Param("warehouseId") int warehouseId, @Param("productId") int productId);

    // warehouse_detail 테이블에서 수량 업데이트
    void updateWarehouseProductQuantity(@Param("warehouseId") int warehouseId,
                                        @Param("productId") int productId,
                                        @Param("additionalQuantity") int additionalQuantity);

    // 상품명 가져오기
    String selectProductNameById(@Param("productId") int productId);

    // warehouse_detail에 입고 데이터 추가
    void insertWarehouseDetail(@Param("warehouseId") int warehouseId,
                               @Param("productId") int productId,
                               @Param("warehouseDetailQuantity") int warehouseDetailQuantity);

    // 입고 로그 기록
    void insertWarehouseReceiveLog(StorageDto storage);
}