package com.minisec.user.service;

import com.minisec.user.model.dao.cart.CartDao;
import com.minisec.user.model.dto.cart.CartDetailByStoreDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;
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

    public int deleteCartList(CartProductDeleteDto deleteList){
        try(SqlSession sqlSession = getSqlSession()){
            cartDao = sqlSession.getMapper(CartDao.class);

            int result = cartDao.deleteCartList(deleteList);
            if(result == 0){
                sqlSession.rollback();
                return 0;
            }
            sqlSession.commit();
            return 1;
        }

    }

    public int deleteAllCartListByUserId(int userId){
        try(SqlSession sqlSession = getSqlSession()){
            cartDao = sqlSession.getMapper(CartDao.class);

            int executeResult = cartDao.selectAllCartCountByUserId(userId);
            int result = cartDao.deleteAllCartListByUserId(userId);

            if(result != executeResult){
                sqlSession.rollback();
                return 0;
            }
            sqlSession.commit();
            return 1;
        }
    }

}
