package com.minisec.warehouse.service;
import com.minisec.warehouse.model.dao.StorageMapper;
import com.minisec.warehouse.model.dto.StorageDto;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import java.util.Random;

import static com.minisec.common.Template.getSqlSession;


public class StorageService {

    // 입고된 모든 상품
    public List<StorageDto> selectAllStorage(){
        SqlSession sqlSession = getSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);
        List<StorageDto> list = storageMapper.selectAllStorage();
        sqlSession.close();
        return list;
    }

    // 불량품 제외 계산
    public int calculateFinalStorageQuantity(StorageDto storage){
        Random rand = new Random();
        int min = (int) (storage.getStorageQuantity() * 0.75); // 최소 75% 입고
        int max = storage.getStorageQuantity(); // 최대 100% 입고
        return rand.nextInt((max - min) + 1) + min;
    }

    // 최종 입고 수량 db 업데이트
    public void updateStorageQuantity(int storageId, int newQuantity) {
        SqlSession sqlSession = getSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        try {
            storageMapper.updateStorageQuantity(storageId, newQuantity);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    public void insertWarehouseProduct(StorageDto storage) {

        SqlSession sqlSession = getSqlSession();
        StorageMapper mapper = sqlSession.getMapper(StorageMapper.class);

        try {
            mapper.insertWarehouseReceiveLog(storage);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
