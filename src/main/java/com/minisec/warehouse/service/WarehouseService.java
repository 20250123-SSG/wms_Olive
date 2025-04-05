package com.minisec.warehouse.service;

import com.minisec.warehouse.model.dao.WarehouseMapper;
import com.minisec.warehouse.model.dto.WarehouseDto;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class WarehouseService {

    private WarehouseMapper warehouseMapper;

    // 창고 전체 상품 조회
    public List<WarehouseDto> selectAllProducts() {
        SqlSession sqlSession = getSqlSession();
        warehouseMapper = sqlSession.getMapper(WarehouseMapper.class);

        List<WarehouseDto> list = warehouseMapper.getProductList();

        sqlSession.close();
        return list;
    }

}
