package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.common.UserInformationEditOption;
import com.minisec.user.model.dto.order.UserBalanceUpdateDto;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;
import com.minisec.user.service.UserInformationService;
import com.minisec.user.view.printer.UpdateStatusPrinter;
import com.minisec.user.view.printer.user.UserInformationPrinter;
import com.minisec.user.view.printer.user.UserUpdateInformationPrinter;

public class UserManagementController {

    private final UserInformationService userInfoService = new UserInformationService();

    public UserManagementController() {
    }


    public Login selectUserByUserId(Login user){
        return userInfoService.selectUserByUserId(user.getUserId());
    }

    public void updateUserInformationByFilter(Login user,
                                              UserInformationEditOption editOption,
                                              String before, String after){
        UserInformationEditFilterDto filterDto = new UserInformationEditFilterDto();
        filterDto.setUserId(user.getUserId());

        switch (editOption) {
            case ID :       filterDto.setUserLoginId(after); break;
            case PASSWORD : filterDto.setUserPwd(after);     break;
            case NAME :     filterDto.setUserName(after);    break;
            case PHONE :    filterDto.setUserPhone(after);   break;
            case ADDRESS :  filterDto.setUserAddress(after); break;
            default: return;
        }

        boolean updateResult = userInfoService.updateUserInformationByFilter(filterDto);
        if(!updateResult){
            UpdateStatusPrinter.printUpdateUserInfo(false);
            return;
        }
        UpdateStatusPrinter.printUpdateUserInfo(true);
        UserUpdateInformationPrinter.print(before,after);
    }

    public void chargingBalance(Login user, String inputChargingBalance) {
        boolean updateResult = userInfoService.updateUserBalance(new UserBalanceUpdateDto(
                user.getUserId(),
                Integer.parseInt(inputChargingBalance)
        ));

        if (!updateResult) {
            UpdateStatusPrinter.printUpdateBalanceInfo(false);
            return;
        }
        UpdateStatusPrinter.printUpdateBalanceInfo(true);
        user = userInfoService.selectUserByUserId(user.getUserId());
        UserInformationPrinter.printBalance(user);
    }

}
