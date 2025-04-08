package com.minisec.user.model.dao.user;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;

public interface UserDao {

    Login selectUserByUserId(int userId);

    int updateUserBalance(UserBalanceUpdateDto userBalanceUpdateDetail);

    int updateUserInformationByFilter(UserInformationEditFilterDto userInformationEditFilterDto);

}
