package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.WarehouseReceiveLogDto;
import com.minisec.warehouse.model.dto.WarehouseShipmentLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehouseProductMapper {
    List<WarehouseProductDetailDto> selectAllProducts(int warehouseId);

    List<WarehouseShipmentLogDto> getShippingProductLog(int searchProductId);

    List<WarehouseReceiveLogDto> getReceiveProductLog(int searchProductId);

    int updateWarehouseDetail(@Param("warehouseId") int warehouseId, @Param("productId") int productId, @Param("quantity") int quantity);

    int getProductQuantity(@Param("warehouseId") int warehouseId, @Param("productId") int productId);

    int insertWarehouseShipmentLog(WarehouseShipmentLogDto shipmentLog);
}