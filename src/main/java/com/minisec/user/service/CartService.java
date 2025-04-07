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
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            return cartDao.selectAllCartDetailListByUserId(userId);
        }
    }

    public void insertCartList(List<CartDto> cartList) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            for (CartDto cart : cartList) {
                Integer existCartId = cartDao.selectCartId(cart);

                if(existCartId == null){
                    int insertNewCartResult = cartDao.insertCart(cart);
                    if(insertNewCartResult != 1){
                        sqlSession.rollback();
                        throw new IllegalArgumentException("장바구니 추가에 실패하였습니다.");
                    }
                }else {
                    cart.setCartId(existCartId);
                    int updateExistedCartResult = cartDao.updateCartByCartId(cart);
                    if(updateExistedCartResult != 1){
                        sqlSession.rollback();
                        throw new IllegalArgumentException("장바구니 추가에 실패하였습니다.");
                    }
                }
            }

            sqlSession.commit();
        }
    }

    public void deleteCartList(CartProductDeleteDto deleteList) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            int result = cartDao.deleteCartList(deleteList);
            if (result == 0) {
                sqlSession.rollback();
                throw new IllegalArgumentException("장바구니 삭제에 실패하였습니다.");
            }

            sqlSession.commit();
        }
    }

    public void deleteAllCartListByUserId(int userId) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            int executeResult = cartDao.selectAllCartCountByUserId(userId);
            int result = cartDao.deleteAllCartListByUserId(userId);

            if (result != executeResult) {
                sqlSession.rollback();
                throw new IllegalArgumentException("장바구니 삭제에 실패하였습니다.");
            }

            sqlSession.commit();
        }
    }

    public void updateCartProductQuantity(CartDto cart){
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            int result = cartDao.updateCartProductQuantity(cart);
            if (result == 0) {
                sqlSession.rollback();
                throw new IllegalArgumentException("장바구니 수량 수정에 실패하였습니다.");
            }

            sqlSession.commit();
        }
    }

}
