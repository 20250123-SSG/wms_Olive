package com.minisec.store.service;

import com.minisec.store.dao.StoreOrderMapper;
import com.minisec.store.dto.StoreOrderDetailDto;
import com.minisec.store.dto.StoreOrderDto;
import com.minisec.store.dto.StoreOrderProduct;
import com.minisec.store.dto.StoreOrderSelectDto;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class StoreOrderService {
    //private StoreOrderDao storeOrderDao = new StoreOrderDao();
    private StoreOrderMapper storeOrderMapper;

    public int insertOrderStock(StoreOrderDto storeOrderDto) {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        int result = 0;

        try{
            result = storeOrderMapper.insertOrderStock(storeOrderDto);
            if (result <= 0) {
                throw new RuntimeException("발주 실패");
            }
            if(result > 0){
                sqlSession.commit();
            }else{
                sqlSession.rollback();
            }
        }catch (Exception e){
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
        return result;
    }
    public int getLastInsertId() {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        int lastId = storeOrderMapper.getLastInsertId();
        return lastId;
    }
    
    public int insertOrderDetailStock(StoreOrderDetailDto storeOrderDetailDto) {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        int result = storeOrderMapper.insertOrderDetailStock(storeOrderDetailDto);
        if(result > 0){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        return result;

    }

    public List<StoreOrderSelectDto> selectOrderStock() {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        List<StoreOrderSelectDto> result = storeOrderMapper.selectOrderStock();
        return result;
    }

    public int updateOrderStock(StoreOrderDto storeOrderDto) {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        int result = storeOrderMapper.updateOrderStock(storeOrderDto);
        if(result > 0){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        return result;
    }

    public int updateOrderDetailStock(StoreOrderDetailDto storeOrderDetailDto) {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        int result = storeOrderMapper.updateOrderDetailStock(storeOrderDetailDto);
        System.out.println(result);
        if(result > 0){
            sqlSession.commit();
        }else{
            sqlSession.rollback();
        }
        return result;
    }
    public List<StoreOrderProduct> selectStockByOrder() {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        List<StoreOrderProduct> result = storeOrderMapper.selectStockByOrder();
        return result;
    }

}
