package com.minisec.warehouse.controller;

import com.minisec.warehouse.model.dto.ShipmentDto;
import com.minisec.warehouse.service.ShipmentService;
import java.util.List;

public class ShipmentController {
    private ShipmentService shipmentService = new ShipmentService();

    public ShipmentController() {

    }

    public List<ShipmentDto> selectOrderList(int manageId, int choice) {
        return this.shipmentService.getOrderList(manageId, choice);
    }

    public boolean acceptOrder(int orderId) {
        return updateOrderStatus(orderId, '2'); // '2' = 수주
    }

    public boolean rejectOrder(int orderId) {
        return updateOrderStatus(orderId, '3'); // '3' = 거절
    }

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
