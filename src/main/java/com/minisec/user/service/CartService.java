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

    /**
     * 1. user_id + store_id + product_id 가 있는지 select + cart_id 받아오기 - selectCart
     * 2. 만약에 cart_id가 있으면 cart_id가지고 insert - updateCartList
     * 3. 없다면 새롭게 insert - insertCartList
     *
     * OrderProductDto는 하나씩 하위에 있어야한다. - 존재하는지 확ㅇ닌해야하기 때문
     *
     * 있 : CartDto에 select해온 cartId넣어서 update
     * 없 : 그냥 insert
     *
     * -> 다 단일로 해야겠네
     * @param cartList
     * @return
     */
    public int insertCartList(List<CartDto> cartList) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            for (CartDto cart : cartList) {
                Integer existCartId = cartDao.selectCartId(cart);

                if(existCartId == null){
                    int insertNewCartResult = cartDao.insertCart(cart);
                    if(insertNewCartResult != 1){
                        sqlSession.rollback();
                        return 0;
                    }
                }else {
                    cart.setCartId(existCartId);
                    int updateExistedCartResult = cartDao.updateCartByCartId(cart);
                    if(updateExistedCartResult != 1){
                        sqlSession.rollback();
                        return 0;
                    }
                }
            }

            sqlSession.commit();
            return 1;
        }
    }

    public int deleteCartList(CartProductDeleteDto deleteList) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            int result = cartDao.deleteCartList(deleteList);
            if (result == 0) {
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }

    public int deleteAllCartListByUserId(int userId) {
        try (SqlSession sqlSession = getSqlSession()) {
            cartDao = sqlSession.getMapper(CartDao.class);

            int executeResult = cartDao.selectAllCartCountByUserId(userId);
            int result = cartDao.deleteAllCartListByUserId(userId);

            if (result != executeResult) {
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }

}
