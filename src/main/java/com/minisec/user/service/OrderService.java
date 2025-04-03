package com.minisec.user.service;


import com.minisec.user.model.dao.UserDao;
import com.minisec.user.model.dao.cart.CartDao;
import com.minisec.user.model.dao.order.OrderDao;
import com.minisec.user.model.dao.order.OrderDetailDao;
import com.minisec.user.model.dao.order.StoreProductDao;
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
   // private UserDao userDao

    ///바로 구매
    /**
     * 1. StoreDao(재고차감)
     * 2. UserDao(금액차감)
     * 3. OrderDao(주문추가)
     * 4. OrderDao(주문상품추가)
     */
    public int insertOrderList(InsertOrderDto request) {
        try(SqlSession sqlSession = getSqlSession()){
            initDaos(sqlSession);

            if(!decreaseStoreProductsQuantity(request.storeInventoryDeductionList())){
                sqlSession.rollback();
                return 0;
            }
            if(!decreaseUserBalance(request.userBalanceUpdateList())){
                sqlSession.rollback();
                return 0;
            }

            List<OrderDto> orderList = request.orderDtoList();
            if(!insertOrderListAndGetOrderId(orderList)){
                sqlSession.rollback();
                return 0;
            }
            if(!insertOrderDetailList(orderList)){
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }

    public boolean insertOrderDetailList(List<OrderDto> orderList){
        int insertCheckCount = 0;
        for(OrderDto orderDto : orderList){
            List<OrderProductDto> orderProductList = orderDto.getOrderProducts();
            insertCheckCount += orderProductList.size();
        }
        int insertOrderDetailResult = orderDetailDao.insertOrderDetailList(orderList);
        if(insertCheckCount == insertOrderDetailResult){
            return true;
        }
        return false;

    }

    public boolean insertOrderListAndGetOrderId(List<OrderDto> orderList){
        int insertOrderResult = 0;
        for(OrderDto orderDto : orderList){
            insertOrderResult += orderDao.insertOrder(orderDto);
        }
        if(insertOrderResult == orderList.size()){
            return true;
        }
        return false;
    }

    public boolean decreaseUserBalance(List<UserBalanceUpdateDto> updateUserBalanceList){
        int updateUserBalanceDeductionResult = 0;
        for(UserBalanceUpdateDto userBalanceUpdateDto : updateUserBalanceList){
            updateUserBalanceDeductionResult += userDao.updateUserBalance(userBalanceUpdateDto);
        }
        if(updateUserBalanceDeductionResult == updateUserBalanceList.size() ){
            return true;
        }
        return false;
    }

    public boolean decreaseStoreProductsQuantity(List<StoreInventoryDeductionDto> updateStoreProductQuantityList){
        int updateStoreProductQuantityResult = 0;
        for(StoreInventoryDeductionDto storeProductDto : updateStoreProductQuantityList){
            updateStoreProductQuantityResult += storeProductDao.decreaseStoreProductQuantity(storeProductDto);
        }
        if(updateStoreProductQuantityResult == updateStoreProductQuantityList.size()){
            return true;
        }
        return false;
    }


    /// 장바구니 구매
    public void insertOrderFromCart(){
       // insertOrder();

    }

    private void initDaos(SqlSession sqlSession){
        storeProductDao = sqlSession.getMapper(StoreProductDao.class);
        userDao = sqlSession.getMapper(UserDao.class);
        orderDao = sqlSession.getMapper(OrderDao.class);
        orderDetailDao = sqlSession.getMapper(OrderDetailDao.class);
        cartDao = sqlSession.getMapper(CartDao.class);
    }

    public void selectOrderListByUserId(int userId){
       try(SqlSession sqlSession = getSqlSession()){
           orderDao = sqlSession.getMapper(OrderDao.class);
           /**
            * 사용자 주문 아이디들 가져오기
            * 조인해서 List<OrderDto>로 받아오기
            *
            */

           List<OrderDto> userOrderIdList = orderDao.selectOrderListByUserId(userId);

       }
    }



}
