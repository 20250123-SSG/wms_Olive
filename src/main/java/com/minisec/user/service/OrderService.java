package com.minisec.user.service;


import com.minisec.user.model.dao.UserDao;
import com.minisec.user.model.dao.cart.CartDao;
import com.minisec.user.model.dao.order.OrderDao;
import com.minisec.user.model.dao.order.OrderDetailDao;
import com.minisec.user.model.dao.order.StoreProductDao;
import com.minisec.user.model.dto.cart.CartOrderProcessDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;
import com.minisec.user.model.dto.order.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class OrderService {

    private StoreProductDao storeProductDao;
    private UserDao userDao;
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private CartDao cartDao;


    public List<OrderDto> selectAllOrderListByFilter(OrderDetailFilterDto orderDetailFilter){
        try(SqlSession sqlSession = getSqlSession()) {
            orderDao = sqlSession.getMapper(OrderDao.class);

            return orderDao.selectAllOrderListByFilter(orderDetailFilter); //이거를
        }
    }

    //재사용가능할거같다.
    public List<OrderDto> selectAllOrderDetailListByFilter(OrderDetailFilterDto orderDetailFilter) {
        try (SqlSession sqlSession = getSqlSession()) {
            orderDao = sqlSession.getMapper(OrderDao.class);

            List<OrderDto> orderList = orderDao.selectAllOrderDetailListByFilter(orderDetailFilter);
            return orderList;
        }
    }

    public int order(OrderProcessDto request){
        try (SqlSession sqlSession = getSqlSession()) {

            int insertOrderListResult = insertOrderList(request,sqlSession);
            if(insertOrderListResult == 0){
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }


    public int orderFromCart(CartOrderProcessDto request) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            int insertOrderListResult = insertOrderList(request.orderProcessDto(),sqlSession);
            if(insertOrderListResult == 0){
                sqlSession.rollback();
                return 0;
            }

            CartProductDeleteDto cartProductDeleteList = request.productDeleteList();

            int deleteFromCart = cartDao.deleteCartList(cartProductDeleteList);
            if(deleteFromCart == 0){
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }

    private int insertOrderList(OrderProcessDto request, SqlSession sqlSession) {
        storeProductDao = sqlSession.getMapper(StoreProductDao.class);
        userDao = sqlSession.getMapper(UserDao.class);
        orderDao = sqlSession.getMapper(OrderDao.class);
        orderDetailDao = sqlSession.getMapper(OrderDetailDao.class);

        if (!decreaseStoreProductsQuantity(request.storeInventoryDeductionList())) {
            return 0;
        }
        if (!decreaseUserBalance(request.userBalanceUpdateList())) {
            return 0;
        }

        List<OrderDto> orderList = request.orderDtoList();
        if (!insertOrderListAndGetOrderId(orderList)) {
            return 0;
        }
        if (!insertOrderDetailList(orderList)) {
            return 0;
        }

        return 1;
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

    private boolean insertOrderListAndGetOrderId(List<OrderDto> orderList) {
        int insertOrderResult = 0;
        for (OrderDto orderDto : orderList) {
            insertOrderResult += orderDao.insertOrder(orderDto);
        }
        return insertOrderResult == orderList.size();
    }

    private boolean decreaseUserBalance(List<UserBalanceUpdateDto> updateUserBalanceList) {
        int updateUserBalanceDeductionResult = 0;
        for (UserBalanceUpdateDto userBalanceUpdateDto : updateUserBalanceList) {
            updateUserBalanceDeductionResult += userDao.updateUserBalance(userBalanceUpdateDto);
        }
        return updateUserBalanceDeductionResult == updateUserBalanceList.size();
    }

    private boolean decreaseStoreProductsQuantity(List<StoreInventoryDeductionDto> updateStoreProductQuantityList) {
        int updateStoreProductQuantityResult = 0;
        for (StoreInventoryDeductionDto storeProductDto : updateStoreProductQuantityList) {
            updateStoreProductQuantityResult += storeProductDao.decreaseStoreProductQuantity(storeProductDto);
        }
        return updateStoreProductQuantityResult == updateStoreProductQuantityList.size();
    }

}
