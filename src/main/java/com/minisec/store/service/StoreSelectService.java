package com.minisec.store.service;

import com.minisec.store.dao.StoreProductMapper;
import com.minisec.store.dto.StoreProductDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class StoreSelectService {

    private StoreProductMapper storeProductMapper;

    public List<StoreProductDto> selectProductListAll(int manageId) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectAllProduct(manageId);
        sqlSession.close();
        return list;
    }

    public List<StoreProductDto> selectSearchNameList(int manageId, String searchName) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectNameSearch(manageId, searchName);
        sqlSession.close();
        return list;
    }

    public List<StoreProductDto> selectSearchCategoryList(int manageId, String searchCategory) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectCategorySearch(manageId, searchCategory);
        sqlSession.close();
        return list;
    }

    public List<StoreProductDto> selectSearchLowList(int manageId) {
        SqlSession sqlSession = getSqlSession();
        storeProductMapper = sqlSession.getMapper(StoreProductMapper.class);
        List<StoreProductDto> list = storeProductMapper.selectProductLowStock(manageId);
        sqlSession.close();
        return list;
    }
}
