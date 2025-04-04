package com.minisec.user.model.dao;

import com.minisec.user.model.dto.order.UserBalanceUpdateDto;

public interface UserDao {
    int updateUserBalance(UserBalanceUpdateDto userBalanceUpdateDetail);
}
