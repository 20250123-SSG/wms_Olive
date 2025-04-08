package com.minisec.warehouse.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.minisec.warehouse.model.dto.ShipmentDto;

public interface ShipmentMapper {
    List<ShipmentDto> selectOrderList(@Param("warehouseId") int warehouseId, @Param("choice") int choice);

    // 주문 상태 업데이트
    void updateOrderStatus(@Param("orderId") int orderId, @Param("status") char status);

    // 발주 시, 상품 수량 차감
    void updateOrderStatusAndQuantity(@Param("orderId") int orderId, @Param("status") char status);
}
