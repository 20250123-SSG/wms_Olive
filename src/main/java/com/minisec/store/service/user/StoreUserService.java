package com.minisec.store.service.user;

import com.minisec.store.model.dao.user.StoreUserMapper;
import com.minisec.store.model.dto.user.StoreUserSelectDto;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.minisec.common.Template.getSqlSession;

public class StoreUserService {
    private StoreUserMapper storeUserMapper;


    public List<StoreUserSelectDto> selectStoreUserStock(int storeId) {
        SqlSession sqlSession = getSqlSession();
        storeUserMapper = sqlSession.getMapper(StoreUserMapper.class);
        List<StoreUserSelectDto> result = storeUserMapper.selectStoreUserStock(storeId);
        return result;
    }

    public List<StoreUserSelectDto> selectStoreUserStockWithStatus(int storeId) {
        SqlSession sqlSession = getSqlSession();
        storeUserMapper = sqlSession.getMapper(StoreUserMapper.class);
        List<StoreUserSelectDto> result = storeUserMapper.selectStoreUserStockWithStatus(storeId);
        return result;
    }



    public boolean updateUserOrderStatusByDetailId(int userOrderDetailId, String newStatus) {
        SqlSession sqlSession = getSqlSession();
        storeUserMapper = sqlSession.getMapper(StoreUserMapper.class);
        try {
            // userOrderId 조회
            int userOrderId = getUserOrderIdByDetailId(userOrderDetailId);

            // 주문 상태 업데이트
            updateOrderStatus(userOrderId, newStatus);

            if ("1".equals(newStatus)) {
                System.out.println("승인이 완료되었습니다.");
            }

            // 주문 업데이트 (newStatus가 'N'일 때만)
            if ("2".equals(newStatus)) {
                updateInventory(userOrderDetailId);
                System.out.println("승인이 취소되었습니다.");
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

    public int getUserOrderIdByDetailId(int userOrderDetailId) throws SQLException {
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
