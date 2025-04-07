package com.minisec.user.model.dao;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.order.UserBalanceUpdateDto;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;

public interface UserDao {

    Login selectUserByUserId(int userId);

    int updateUserBalance(UserBalanceUpdateDto userBalanceUpdateDetail);

    int updateUserInformationByFilter(UserInformationEditFilterDto userInformationEditFilterDto);

}
