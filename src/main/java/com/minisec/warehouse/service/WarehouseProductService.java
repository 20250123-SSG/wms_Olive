package com.minisec.warehouse.service;

import com.minisec.warehouse.model.dao.WarehouseProductMapper;
import com.minisec.warehouse.model.dto.WarehouseLogDto;
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

    public List<WarehouseLogDto> selectSearchProductLog(int searchProductId) {
        SqlSession session = getSqlSession();
        try{
            WarehouseProductMapper mapper = session.getMapper(WarehouseProductMapper.class);
            return mapper.getProductLog(searchProductId);
        } finally {
            session.close();
        }
    }
}
