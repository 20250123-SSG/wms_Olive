package com.minisec.user.controller;

import com.minisec.common.login.Login;
import com.minisec.user.common.UserInformationEditOption;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;
import com.minisec.user.service.UserInformationService;
import com.minisec.user.view.printer.ExceptionPrinter;
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
                                              String before,
                                              String after){

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

        try {
            userInfoService.updateUserInformationByFilter(filterDto);
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
            return;
        }

        UpdateStatusPrinter.printUpdateUserInfo(true);
        UserUpdateInformationPrinter.print(before,after);
    }


    public void chargingBalance(Login user, String inputChargingBalance) {
        int chargingBalanceInt = 0;

        try {
            chargingBalanceInt = Integer.parseInt(inputChargingBalance);
            if(chargingBalanceInt < 0){
                throw new IllegalArgumentException("충전 금액은 양수만 입력 가능합니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("충전 금액은 숫자로 입력해주세요.");
        }

        try {
            userInfoService.updateUserBalance(new UserBalanceUpdateDto(
                    user.getUserId(),
                    chargingBalanceInt
            ));
        } catch (IllegalArgumentException e) {
            ExceptionPrinter.print(e.getMessage());
            return;
        }

        UpdateStatusPrinter.printUpdateBalanceInfo(true);
        user = userInfoService.selectUserByUserId(user.getUserId());
        UserInformationPrinter.printBalance(user);
    }

}
