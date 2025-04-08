package com.minisec.warehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minisec.warehouse.model.dto.ShipmentDto;
import com.minisec.warehouse.service.ShipmentService;
import com.minisec.warehouse.view.WarehouseResultView;


public class ShipmentController {
    private final ShipmentService shipmentService = new ShipmentService();

    public Map<Integer, Integer> selectOrderList(int manageId, int choice) {
        List<ShipmentDto> orders = shipmentService.getOrderList(manageId, choice);
        if (orders.isEmpty()) {
            System.out.println("조회된 발주 내역이 없습니다.");
            return new HashMap<>();
        } else {
            WarehouseResultView.displayShipmentList(orders);
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < orders.size(); i++) {
                ShipmentDto order = orders.get(i);
                map.put(i, order.getStoreOrderId());
            }
            return map;
        }
    }

    public boolean acceptOrder(int orderId) {
        return updateOrderStatus(orderId, '2'); // '2' = 수주
    }

   /* public boolean rejectOrder(int orderId) {
        return updateOrderStatus(orderId, '3'); // '3' = 거절
    }*/

    private boolean updateOrderStatus(int orderId, char status) {
        try {
            shipmentService.updateOrderStatus(orderId, status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
