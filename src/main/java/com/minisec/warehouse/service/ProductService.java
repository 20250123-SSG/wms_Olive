package com.minisec.warehouse.service;

import com.minisec.warehouse.model.dao.ProductMapper;
import com.minisec.warehouse.model.dto.ProductDto;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class ProductService {

    private ProductMapper productMapper;

    // 창고 전체 상품 조회
    public List<ProductDto> selectAllProducts() {
        SqlSession sqlSession = getSqlSession();
        productMapper = sqlSession.getMapper(ProductMapper.class);

        List<ProductDto> list = productMapper.getProductList();

        sqlSession.close();
        return list;
    }
}
