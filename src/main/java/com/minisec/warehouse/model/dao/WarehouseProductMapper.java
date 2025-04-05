package com.minisec.warehouse.model.dao;

import com.minisec.warehouse.model.dto.WarehouseLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;

import java.util.List;

public interface WarehouseProductMapper {
    List<WarehouseProductDetailDto> selectAllProducts(int warehouseId);
    List<WarehouseLogDto> getProductLog(int searchProductId);
}