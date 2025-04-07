package com.minisec.user.model.dao.cart;

import com.minisec.user.model.dto.cart.CartDetailByStoreDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;

import java.util.List;

public interface CartDao {

    Integer selectCartId(CartDto cart);

    int selectAllCartCountByUserId(int userId);

    List<CartDetailByStoreDto> selectAllCartDetailListByUserId(int userId);

    int insertCart(CartDto cart);

    int updateCartByCartId(CartDto cart);

    int updateCartProductQuantity(CartDto cart);

    int deleteCartList(CartProductDeleteDto deleteList);

    int deleteAllCartListByUserId(int userId);

}
