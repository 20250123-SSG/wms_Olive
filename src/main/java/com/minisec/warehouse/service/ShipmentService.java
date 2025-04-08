package com.minisec.warehouse.service;

import java.util.List;

import com.minisec.warehouse.model.dao.ShipmentMapper;
import com.minisec.warehouse.model.dto.ShipmentDto;
import org.apache.ibatis.session.SqlSession;
import static com.minisec.common.Template.getSqlSession;

public class ShipmentService {

    public List<ShipmentDto> getOrderList(int manageId, int choice) {
        try (SqlSession sqlSession = getSqlSession()) {
            ShipmentMapper shipmentMapper = sqlSession.getMapper(ShipmentMapper.class);
            return shipmentMapper.selectOrderList(manageId, choice);
        }
    }


    public void updateOrderStatus(int orderId, char status) {
//        try (SqlSession sqlSession = getSqlSession()) {
//            shipmentDao = sqlSession.getMapper(ShipmentDao.class);
//            shipmentDao.updateOrderStatusAndQuantity(orderId, status);
//            sqlSession.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("주문 상태 업데이트 중 오류 발생");
//        }
    }


}
