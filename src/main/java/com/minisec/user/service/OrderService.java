package com.minisec.user.service;


import com.minisec.user.model.dao.user.UserDao;
import com.minisec.user.model.dao.cart.CartDao;
import com.minisec.user.model.dao.order.OrderDao;
import com.minisec.user.model.dao.order.OrderDetailDao;
import com.minisec.user.model.dao.store.StoreProductDao;
import com.minisec.user.model.dto.OrderProductDto;
import com.minisec.user.model.dto.cart.CartOrderProcessDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;
import com.minisec.user.model.dto.order.*;
import com.minisec.user.model.dto.store.StoreInventoryDeductionDto;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class OrderService {

    private StoreProductDao storeProductDao;
    private UserDao userDao;
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private CartDao cartDao;


    public List<OrderDto> selectAllOrderListByFilter(OrderDetailFilterDto orderDetailFilter) {
        try (SqlSession sqlSession = getSqlSession()) {
            orderDao = sqlSession.getMapper(OrderDao.class);

            return orderDao.selectAllOrderListByFilter(orderDetailFilter);
        }
    }


    public List<OrderDto> selectAllOrderDetailListByFilter(OrderDetailFilterDto orderDetailFilter) {
        try (SqlSession sqlSession = getSqlSession()) {
            orderDao = sqlSession.getMapper(OrderDao.class);

            return orderDao.selectAllOrderDetailListByFilter(orderDetailFilter);
        }
    }


    public void order(OrderProcessDto request) {
        try (SqlSession sqlSession = getSqlSession()) {

            try {
                insertOrderList(request, sqlSession);
            } catch (IllegalArgumentException e) {
                sqlSession.rollback();
                throw e;
            }

            sqlSession.commit();
        }
    }


    public void orderFromCart(CartOrderProcessDto request) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            try {
                insertOrderList(request.orderProcessDto(), sqlSession);
            } catch (IllegalArgumentException e) {
                sqlSession.rollback();
                throw e;
            }

            CartProductDeleteDto cartProductDeleteList = request.productDeleteList();

            int deleteFromCart = cartDao.deleteCartList(cartProductDeleteList);
            if (deleteFromCart == 0) {
                sqlSession.rollback();
                throw new IllegalArgumentException("주문에 실패하였습니다.");
            }

            sqlSession.commit();
        }
    }


    private void insertOrderList(OrderProcessDto request, SqlSession sqlSession) {
        storeProductDao = sqlSession.getMapper(StoreProductDao.class);
        userDao = sqlSession.getMapper(UserDao.class);
        orderDao = sqlSession.getMapper(OrderDao.class);
        orderDetailDao = sqlSession.getMapper(OrderDetailDao.class);

        if (!decreaseStoreProductsQuantity(request.storeInventoryDeductionList())) {
            throw new IllegalArgumentException("수량이 부족합니다.");
        }
        if (!decreaseUserBalance(request.userBalanceUpdateList())) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        List<OrderDto> orderList = request.orderDtoList();
        if (!insertOrderListAndGetOrderId(orderList)) {
            throw new IllegalArgumentException("주문에 실패하였습니다.");
        }
        if (!insertOrderDetailList(orderList)) {
            throw new IllegalArgumentException("주문에 실패하였습니다.");
        }
    }


    private boolean decreaseStoreProductsQuantity(List<StoreInventoryDeductionDto> updateStoreProductQuantityList) {
        int updateStoreProductQuantityResult = 0;
        for (StoreInventoryDeductionDto storeProductDto : updateStoreProductQuantityList) {
            updateStoreProductQuantityResult += storeProductDao.decreaseStoreProductQuantity(storeProductDto);
        }
        return updateStoreProductQuantityResult == updateStoreProductQuantityList.size();
    }


    private boolean decreaseUserBalance(List<UserBalanceUpdateDto> updateUserBalanceList) {
        int updateUserBalanceDeductionResult = 0;
        for (UserBalanceUpdateDto userBalanceUpdateDto : updateUserBalanceList) {
            updateUserBalanceDeductionResult += userDao.updateUserBalance(userBalanceUpdateDto);
        }
        return updateUserBalanceDeductionResult == updateUserBalanceList.size();
    }


    private boolean insertOrderListAndGetOrderId(List<OrderDto> orderList) {
        int insertOrderResult = 0;
        for (OrderDto orderDto : orderList) {
            insertOrderResult += orderDao.insertOrder(orderDto);
        }
        return insertOrderResult == orderList.size();
    }


    private boolean insertOrderDetailList(List<OrderDto> orderList) {
        int insertCheckCount = 0;
        for (OrderDto orderDto : orderList) {
            List<OrderProductDto> orderProductList = orderDto.getOrderProducts();
            insertCheckCount += orderProductList.size();
        }
        int insertOrderDetailResult = orderDetailDao.insertOrderDetailList(orderList);
        return insertCheckCount == insertOrderDetailResult;
    }

}
