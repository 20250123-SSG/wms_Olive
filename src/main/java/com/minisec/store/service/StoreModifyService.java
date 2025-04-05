package com.minisec.store.service;

import com.minisec.store.dao.StoreModifyMapper;
import com.minisec.store.dto.StoreProductDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class StoreModifyService {
    private static StoreModifyMapper storeModifyMapper;


    public List<StoreProductDto> ProductList() {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);
        List<StoreProductDto> list = storeModifyMapper.productList();
        sqlSession.close();
        return list;
    }

    public static int insertStoreProduct(StoreProductDto storeProduct) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);

        int result = 0;

        try {
            result = storeModifyMapper.insertStoreProduct(storeProduct);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return result;
    }


    public List<StoreProductDto> selectStoreProductById(String productId) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);
        List<StoreProductDto> list = storeModifyMapper.selectStoreProductById();
        sqlSession.close();
        return list;
    }
}


