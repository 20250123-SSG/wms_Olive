package com.minisec.store.service;

import com.minisec.store.dao.StoreProductMapper;
import com.minisec.store.dto.StoreProductDto;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.minisec.common.Template.getSqlSession;

public class StoreSelectService {

    private StoreProductMapper storeProductMapper;

    public List<StoreProductDto> selectAllProduct(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectAllProduct(param);
        sqlSession.close();
        return list;
    }

    public List<StoreProductDto> selectSearchNameList(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectNameSearch(param);
        sqlSession.close();
        return list;
    }


    public List<StoreProductDto> selectSearchCategoryList(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectCategorySearch(param);
        sqlSession.close();
        return list;
    }

    public List<StoreProductDto> selectSearchLowList(Map<String, Object> param) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectProductLowStock(param);
        sqlSession.close();
        return list;
    }
}
