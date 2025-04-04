package com.minisec.user.service;

import com.minisec.user.model.dao.order.StoreDao;
import com.minisec.user.model.dao.order.StoreProductDao;
import com.minisec.user.model.dto.StoreProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class StoreService {
    private StoreDao storeDao;
    private StoreProductDao storeProductDao;

    public List<StoreDto> selectStoreList() {
        try (SqlSession sqlSession = getSqlSession()) {
            storeDao = sqlSession.getMapper(StoreDao.class);

            return storeDao.selectStoreList();
        }
    }

    public List<StoreProductDto> selectStoreAllProductByStoreId(int storeId) {
        try (SqlSession sqlSession = getSqlSession()) {
            storeProductDao = sqlSession.getMapper(StoreProductDao.class);

            return storeProductDao.selectStoreAllProductByStoreId(storeId);
        }
    }

}
