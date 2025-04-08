package com.minisec.store.service;


import com.minisec.store.model.dao.user.StoreUserMapper;
import com.minisec.store.model.dto.user.StoreUserSelectDto;
import org.apache.ibatis.session.SqlSession;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.minisec.common.Template.getSqlSession;

public class StoreUserService {
    //private StoreOrderDao storeOrderDao = new StoreOrderDao();
    private StoreUserMapper storeUserMapper;

    public List<StoreUserSelectDto> selectStoreUserStock(int storeId) {
        SqlSession sqlSession = getSqlSession();
        storeUserMapper = sqlSession.getMapper(StoreUserMapper.class);
        List<StoreUserSelectDto> result = storeUserMapper.selectStoreUserStock(storeId);
        return result;
    }

    public boolean updateUserOrderStatusByDetailId(int userOrderDetailId, String newStatus) {
        SqlSession sqlSession = getSqlSession();
        storeUserMapper = sqlSession.getMapper(StoreUserMapper.class);
        try {
            // userOrderId 조회
            int userOrderId = getUserOrderId(userOrderDetailId);

            // 주문 상태 업데이트
            updateOrderStatus(userOrderId, newStatus);

            // 주문 업데이트 (newStatus가 'N'일 때만)
            if ("N".equals(newStatus)) {
                updateInventory(userOrderId);
            }
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            throw new RuntimeException("주문 상태 업데이트 중 오류 발생", e);
        } finally {
            sqlSession.close();
        }
    }

    private int getUserOrderId(int userOrderDetailId) throws SQLException {
        int userOrderId = storeUserMapper.getUserOrderIdByDetailId(userOrderDetailId);
        if (userOrderId <= 0) {
            throw new SQLException("유효하지 않은 userOrderDetailId:");
        }
        return userOrderId;
    }

    public void updateOrderStatus(int userOrderId, String newStatus) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("userOrderId", userOrderId);
        params.put("newStatus", newStatus);

        int result = storeUserMapper.updateUserOrderStatus(params);
        if (result <= 0) {
            throw new SQLException("주문 상태 업데이트 실패: userOrderId");
        }
    }

    public void updateInventory(int userOrderId) throws SQLException {
        int result = storeUserMapper.updateStoreDetailQuantity(userOrderId);
        if (result <= 0) {
            throw new SQLException("재고 업데이트 실패: userOrderId=" + userOrderId);
        }
    }

}
