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

    // 입출고 업데이트된 상품 조회
    public void updateProductQuantity(int productId, int warehouseId, int newQuantity) {
        SqlSession sqlSession = getSqlSession();
        productMapper = sqlSession.getMapper(ProductMapper.class);

        productMapper.updateProductQuantity(productId, warehouseId, newQuantity);

        sqlSession.commit();
        sqlSession.close();
    }
}
