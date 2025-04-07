package com.minisec.warehouse.service;

import com.minisec.warehouse.model.dao.WarehouseProductMapper;
import com.minisec.warehouse.model.dto.WarehouseReceiveLogDto;
import com.minisec.warehouse.model.dto.WarehouseShipmentLogDto;
import com.minisec.warehouse.model.dto.WarehouseProductDetailDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class WarehouseProductService {
    public List<WarehouseProductDetailDto> selectAllProducts(int warehouseId) {
        SqlSession session = getSqlSession();
        try {
            WarehouseProductMapper mapper = session.getMapper(WarehouseProductMapper.class);
            return mapper.selectAllProducts(warehouseId);
        } finally {
            session.close();
        }
    }

    public List<WarehouseShipmentLogDto> selectSearchShippingProductLog(int searchProductId) {
        SqlSession session = getSqlSession();
        try{
            WarehouseProductMapper mapper = session.getMapper(WarehouseProductMapper.class);
            return mapper.getShippingProductLog(searchProductId);
        } finally {
            session.close();
        }
    }

    public List<WarehouseReceiveLogDto> selectSearchReceiveProductLog(int searchProductId) {
        SqlSession session = getSqlSession();
        try{
            WarehouseProductMapper mapper = session.getMapper(WarehouseProductMapper.class);
            return mapper.getReceiveProductLog(searchProductId);
        } finally {
            session.close();
        }
    }
}
