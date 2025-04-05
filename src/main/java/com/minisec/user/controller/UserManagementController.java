package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.common.UserInformationEditOption;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;
import com.minisec.user.service.UserInformationService;
import com.minisec.user.view.printer.UpdateStatusPrinter;
import com.minisec.user.view.printer.user.UserInformationPrinter;
import com.minisec.user.view.printer.user.UserUpdateInformationPrinter;

public class UserManagementController {
    private final UserInformationService userInfoService = new UserInformationService();


    public void selectUserByUserId(Login user){
        Login login = userInfoService.selectUserByUserId(user.getUserId());

        UserInformationPrinter.print(login);
    }

    public void updateUserInformationByFilter(Login user,
                                              UserInformationEditOption editOption,
                                              String before, String after){
        UserInformationEditFilterDto filterDto = new UserInformationEditFilterDto();
        filterDto.setUserId(user.getUserId());

        switch (editOption) {
            case ID : filterDto.setUserLoginId(after); break;
            case PASSWORD : filterDto.setUserPwd(after); break;
            case NAME : filterDto.setUserName(after); break;
            case PHONE : filterDto.setUserPhone(after); break;
            case ADDRESS : filterDto.setUserAddress(after); break;
            default: return;
        }

        int updateResult = userInfoService.updateUserInformationByFilter(filterDto);
        if(updateResult == 0){
            UpdateStatusPrinter.printUpdateUserInfo(false);
            return;
        }
        UpdateStatusPrinter.printUpdateUserInfo(true);
        UserUpdateInformationPrinter.print(before,after);
    }

}
