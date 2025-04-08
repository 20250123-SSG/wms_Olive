package com.minisec.warehouse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minisec.warehouse.model.dto.ShipmentDto;
import com.minisec.warehouse.service.ShipmentService;
import com.minisec.warehouse.view.WarehouseResultView;


public class ShipmentController {
    private final ShipmentService shipmentService = new ShipmentService();

    public List<ShipmentDto> selectOrderList(int manageId, int choice) {
        List<ShipmentDto> orders = shipmentService.getOrderList(manageId, choice);
        for (ShipmentDto order : orders) {
            System.out.println(order);
        }
        if (orders.isEmpty()) {
            System.out.println("조회된 발주 내역이 없습니다.");
            return new ArrayList<>();
        } else {
            WarehouseResultView.displayShipmentList(orders);
            return orders;
        }
    }

    public void selecdtOrderDetail(int orderId) {}

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
