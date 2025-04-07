//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.minisec.warehouse.service;

import com.minisec.common.Template;
import com.minisec.warehouse.model.dao.ShipmentDao;
import com.minisec.warehouse.model.dto.ShipmentDto;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class ShipmentService {
    private ShipmentDao shipmentDao;

    public ShipmentService() {
    }

    public void updateOrderStatus(int orderId, char status) {
        try (SqlSession sqlSession = Template.getSqlSession()) {
            shipmentDao = sqlSession.getMapper(ShipmentDao.class);
            shipmentDao.updateOrderStatusAndQuantity(orderId, status);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("주문 상태 업데이트 중 오류 발생");
        }
    }

    public List<ShipmentDto> getOrderList(int manageId, int choice) {
        SqlSession sqlSession = Template.getSqlSession();
        this.shipmentDao = sqlSession.getMapper(ShipmentDao.class);
        return this.shipmentDao.selectOrderList(manageId, choice);
    }

}
