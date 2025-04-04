package com.minisec.user.service;

import com.minisec.common.Template;
import com.minisec.user.model.dao.cart.CartDao;
import com.minisec.user.model.dao.order.OrderDao;
import com.minisec.user.model.dto.cart.CartDetailByStoreDto;
import com.minisec.user.model.dto.cart.CartDto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.minisec.common.Template.getSqlSession;

public class CartService {
    private CartDao cartDao;

    public List<CartDetailByStoreDto> selectAllCartDetailListByUserId(int userId) {
        try(SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            return cartDao.selectAllCartDetailListByUserId(userId);
        }
    }

    public int insertCartList(List<CartDto> cartList){
        try(SqlSession sqlSession = getSqlSession()){
            cartDao = sqlSession.getMapper(CartDao.class);

            int result = 0;
            try {
                result = cartDao.insertCartList(cartList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
            }

            return result;
        }
    }

    /// 선택한 상품 삭제하기
    public void deleteCartsByStoreProductCode(){

    }


    /// 카트 비우기
    public void deleteAllCart(){

    }
}
