package com.minisec.user.view.details;

import com.minisec.common.login.Login;
import com.minisec.user.common.UserInformationEditOption;
import com.minisec.user.controller.UserManagementController;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;

import java.util.Scanner;

public class UserInformationEditView {
    private final Scanner sc = new Scanner(System.in);

    private final UserManagementController userManagementController = new UserManagementController();

    public void run(Login user) {
        while (true) {
            System.out.println("========== 개인 정보 수정 ==========");
            System.out.println("""
                    1. 아이디
                    2. 비번
                    3. 이름
                    4. 전화번호
                    5. 주소
                    0. 뒤로가기
                    """);

            switch (InputFunctionNumberView.input()) {
                case 0 :                                                   return;
                case 1 : update(user, UserInformationEditOption.ID);       break;
                case 2 : update(user, UserInformationEditOption.PASSWORD); break;
                case 3 : update(user, UserInformationEditOption.NAME);     break;
                case 4 : update(user, UserInformationEditOption.PHONE);    break;
                case 5 : update(user, UserInformationEditOption.ADDRESS);  break;
                default:
                    System.out.println("존재하지 않는 기능입니다.");
                    continue;
            }
        }
    }


    private void update(Login user, UserInformationEditOption editOption) {
        String before = null;

        switch (editOption) {
            case ID :       before = user.getUserLoginId(); break;
            case PASSWORD : before = user.getUserPwd();     break;
            case NAME :     before = user.getUserName();    break;
            case PHONE :    before = user.getUserPhone();   break;
            case ADDRESS :  before = user.getUserAddress(); break;
            default: return;
        }
        System.out.printf("변경할 %s를 입력하세요.\n", editOption.getValue());
        System.out.println(">> 입력:");

        userManagementController.updateUserInformationByFilter(
                user,
                editOption,
                before,
                sc.nextLine().trim());
    }

}
