package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.WarehouseReceiveLogDto;
import com.minisec.warehouse.model.dto.WarehouseShipmentLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;

import java.util.List;

public interface WarehouseProductMapper {
    List<WarehouseProductDetailDto> selectAllProducts(int warehouseId);
    List<WarehouseShipmentLogDto> getShippingProductLog(int searchProductId);
    List<WarehouseReceiveLogDto> getReceiveProductLog(int searchProductId);
}