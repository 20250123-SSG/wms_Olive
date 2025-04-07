package com.minisec.warehouse.service;
import com.minisec.warehouse.model.dao.StorageMapper;
import com.minisec.warehouse.model.dto.StorageDto;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import java.util.Random;

import static com.minisec.common.Template.getSqlSession;


public class StorageService {

    // 입고된 모든 상품
    public List<StorageDto> selectAllStorage() {
        try (SqlSession sqlSession = getSqlSession()) {
            StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);
            return storageMapper.selectAllStorage();
        }
    }

    // 불량품 제외 계산
    public int calculateFinalStorageQuantity(StorageDto storage) {
        Random rand = new Random();
        int min = (int) (storage.getStorageQuantity() * 0.75); // 최소 75%
        int max = storage.getStorageQuantity(); // 최대 100%
        return rand.nextInt((max - min) + 1) + min;
    }

    // 최종 입고 수량을 DB에 추가 또는 업데이트
    public void insertOrUpdateWarehouseProduct(int warehouseId, int productId, int finalQuantity) {
        SqlSession sqlSession = getSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        try {
            // warehouse_detail 테이블에서 기존 수량 확인
            Integer existingQuantity = storageMapper.selectWarehouseProductQuantity(warehouseId, productId);

            if (existingQuantity != null) {
                // 기존 제품이 있으면 수량 업데이트
                storageMapper.updateWarehouseProductQuantity(warehouseId, productId, finalQuantity);
            } else {
                // 기존 제품이 없으면 새로 추가
                storageMapper.insertWarehouseDetail(warehouseId, productId, finalQuantity);
            }

            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    // 상품명 조회
    public String getProductNameById(int productId) {
        SqlSession sqlSession = getSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        String productName = storageMapper.selectProductNameById(productId);
        sqlSession.close();
        return productName;
    }

    // 최종 입고 정보를 warehouse_detail 테이블에 추가
    public void insertWarehouseProduct(int warehouseId, int productId, int finalQuantity) {
        SqlSession sqlSession = getSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        try {
            storageMapper.insertWarehouseDetail(warehouseId, productId, finalQuantity);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
