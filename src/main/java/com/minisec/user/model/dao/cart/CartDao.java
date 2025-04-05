package com.minisec.user.model.dao.cart;

import com.minisec.user.model.dto.cart.CartDetailByStoreDto;
import com.minisec.user.model.dto.cart.CartDto;
import com.minisec.user.model.dto.cart.CartProductDeleteDto;

import java.util.List;

public interface CartDao {

    int insertCartList(List<CartDto> cartList);

    List<CartDetailByStoreDto> selectAllCartDetailListByUserId(int userId);

    int deleteCartList(CartProductDeleteDto deleteList);

    int deleteAllCartListByUserId(int userId);

    int selectAllCartCountByUserId(int userId);

}
