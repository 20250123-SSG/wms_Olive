//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.ShipmentDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShipmentDao {
    List<ShipmentDto> selectOrderList(@Param("warehouseId") int var1, @Param("choice") int var2);

    // 주문 상태 업데이트
    void updateOrderStatus(@Param("orderId") int orderId, @Param("status") char status);
}
