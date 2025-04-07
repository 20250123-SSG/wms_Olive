package com.minisec.store.service;

import com.minisec.store.dao.StoreModifyMapper;
import com.minisec.store.dao.StoreProductMapper;
import com.minisec.store.dto.StoreProductDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

import static com.minisec.common.Template.getSqlSession;

public class StoreModifyService {

    private StoreModifyMapper storeModifyMapper;

    public List<StoreProductDto> ProductList(int storeId) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);
        List<StoreProductDto> list = storeModifyMapper.productList(storeId);
        sqlSession.close();
        return list;
    }

    public int insertStoreProduct(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);

        int result = 0;

        try {
            result = storeModifyMapper.insertStoreProduct(param);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public List<StoreProductDto> selectStoreProductById(Map<String, String> param) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);
        List<StoreProductDto> list = storeModifyMapper.selectStoreProductById(param);
        sqlSession.close();
        return list;
    }

    public int updateStoreProduct(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);
        int result = 0;
        try {
            result = storeModifyMapper.updateStoreProduct(param);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally{
            sqlSession.close();
        }

        return result;
    }

    public int deleteStoreProduct(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeModifyMapper = sqlSession.getMapper(StoreModifyMapper.class);
        int result = 0;
        try {
            result = storeModifyMapper.deleteStoreProduct(param);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return result;
    }
}


