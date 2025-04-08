package com.minisec.warehouse.service;

import java.util.List;

import com.minisec.common.product.ProductDto;
import com.minisec.warehouse.model.dto.WarehouseShipmentLogDto;
import com.minisec.warehouse.model.dao.ShipmentMapper;
import com.minisec.warehouse.model.dao.WarehouseProductMapper;
import com.minisec.warehouse.model.dto.ShipmentDetailDto;
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

    public boolean acceptOrder(ShipmentDto shipmentDto, char status) {
        try (SqlSession sqlSession = getSqlSession()) {
            // 주문 상태값 업데이트
            int orderId = shipmentDto.getStoreOrderId();
            ShipmentMapper shipmentMapper = sqlSession.getMapper(ShipmentMapper.class);
            int orderStatus = shipmentMapper.updateOrderStatus(orderId, null, status);

            // 재고 및 로그 업데이트
            List<ProductDto> products = shipmentDto.getProducts();
            List<ShipmentDetailDto> shipmentDetails = shipmentDto.getShipmentDetails();
            int size = Math.max(products.size(), shipmentDetails.size());

            int warehouseStatus = 0;
            int insertLogResult = 0;
            WarehouseProductMapper warehouseProductMapper = sqlSession.getMapper(WarehouseProductMapper.class);
            for (int i = 0; i < size; i++) {
                int warehouseId = shipmentDto.getWarehouseId();
                int productId = shipmentDetails.get(i).getProductId();
                int quantity = shipmentDetails.get(i).getStoreOrderDetailQuantity();
                int nowQuantity = warehouseProductMapper.getProductQuantity(warehouseId, productId);
                if (nowQuantity < quantity) {
                    throw new RuntimeException("재고 부족 에러: 제품 ID " + productId + "의 재고가 부족합니다.");
                }
                warehouseStatus = warehouseProductMapper.updateWarehouseDetail(warehouseId, productId, quantity);

                // 로그 업데이트
                insertLogResult = warehouseProductMapper.insertWarehouseShipmentLog(
                        WarehouseShipmentLogDto.builder()
                                .warehouseId(warehouseId)
                                .storeOrderId(orderId)
                                .productId(productId)
                                .warehouseShippingQuantity(quantity)
                                .build()
                );
            }
            if (orderStatus > 0 && warehouseStatus > 0 && insertLogResult > 0) {
                sqlSession.commit();
                return true;
            } else {
                sqlSession.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("주문 처리 중 오류 발생");
        } finally {
            getSqlSession().close();
        }
    }

    public boolean rejectOrder(ShipmentDto shipmentDto, String memo, char status) {
        try (SqlSession sqlSession = getSqlSession()) {
            int orderId = shipmentDto.getStoreOrderId();
            ShipmentMapper shipmentMapper = sqlSession.getMapper(ShipmentMapper.class);
            System.out.println(orderId);
            System.out.println(memo);
            int orderStatus = shipmentMapper.updateOrderStatus(orderId, memo, status);
            if(orderStatus > 0) {
                sqlSession.commit();
                return true;
            }else{
                sqlSession.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("주문 처리 중 오류 발생");
        } finally {
            getSqlSession().close();
        }
    }
}
