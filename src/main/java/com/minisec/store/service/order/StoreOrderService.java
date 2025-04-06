package com.minisec.store.service.order;

import com.minisec.store.model.dao.order.StoreOrderMapper;
import com.minisec.store.model.dto.order.*;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class StoreOrderService {
    private StoreOrderMapper storeOrderMapper;

    public boolean insertOrderWithDetails(int storeId, String storeOrderSubject, String storeOrderMemo, List<StoreOrderCommonDetailDto> orderDetails) {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        boolean isSuccess = false;

        try {
            // 발주 등록
            StoreOrderCommonDto storeOrderDto = new StoreOrderCommonDto();
            storeOrderDto.setWarehouseId(1); // 기본 창고 ID
            storeOrderDto.setStoreId(storeId);
            storeOrderDto.setStoreOrderSubject(storeOrderSubject);
            storeOrderDto.setStoreOrderMemo(storeOrderMemo);
            storeOrderDto.setStoreOrderStatus("1");
            storeOrderDto.setShipmentDate(LocalDateTime.now());

            int orderResult = storeOrderMapper.insertOrderStock(storeOrderDto);
            if (orderResult <= 0) {
                throw new SQLException("발주 등록 중 에러 발생");
            }

            // 마지막으로 삽입된 발주 ID 가져오기
            int lastOrderId = storeOrderMapper.getLastInsertId();

            // 발주 상세 등록
            for (StoreOrderCommonDetailDto detailDto : orderDetails) {
                detailDto.setStoreOrderId(lastOrderId);
                int detailResult = storeOrderMapper.insertOrderDetailStock(detailDto);
                if (detailResult <= 0) {
                    throw new SQLException("발주 상세 등록 중 에러 발생");
                }
            }
            sqlSession.commit();
            isSuccess = true;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return isSuccess;
    }

    public List<StoreOrderSelectDto> selectOrderStock() {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        List<StoreOrderSelectDto> result = storeOrderMapper.selectOrderStock();
        return result;
    }

    public boolean updateOrderWithDetails(int storeOrderDetailId, int productId, int storeOrderDetailQuantity, String storeOrderSubject, String storeOrderMemo) {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        boolean isSuccess = false;

        try {
            // 발주 ID 조회
            int storeOrderId = storeOrderMapper.getStoreOrderIdByDetailId(storeOrderDetailId);

            // 발주 업데이트
            StoreOrderCommonDto storeOrderDto = new StoreOrderCommonDto();
            storeOrderDto.setStoreOrderId(storeOrderId);
            storeOrderDto.setStoreOrderSubject(storeOrderSubject);
            storeOrderDto.setStoreOrderMemo(storeOrderMemo);
            storeOrderDto.setModifiedAt(LocalDateTime.now());

            int orderResult = storeOrderMapper.updateOrderStock(storeOrderDto);
            if (orderResult <= 0) {
                throw new SQLException("발주 수정 중 에러 발생");
            }

            // 발주 상세 업데이트
            StoreOrderCommonDetailDto storeOrderDetailDto = new StoreOrderCommonDetailDto();
            storeOrderDetailDto.setStoreOrderDetailId(storeOrderDetailId);
            storeOrderDetailDto.setProductId(productId);
            storeOrderDetailDto.setStoreOrderDetailQuantity(storeOrderDetailQuantity);
            storeOrderDetailDto.setModifiedAt(LocalDateTime.now());

            int detailResult = storeOrderMapper.updateOrderDetailStock(storeOrderDetailDto);
            if (detailResult <= 0) {
                throw new SQLException("발주 상세 수정 중 에러 발생");
            }
            sqlSession.commit();
            isSuccess = true;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }

        return isSuccess;
    }

    public List<StoreOrderSelectProductByInsertDto> selectStockByOrder() {
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        List<StoreOrderSelectProductByInsertDto> result = storeOrderMapper.selectStockByOrder();
        return result;
    }

    public List<StoreOrderSelectByUpdateDto> selectStockByUpdate(int storeId){
        SqlSession sqlSession = getSqlSession();
        storeOrderMapper = sqlSession.getMapper(StoreOrderMapper.class);
        List<StoreOrderSelectByUpdateDto> result = storeOrderMapper.selectStockByUpdate(storeId);
        return result;
    }
}
