package com.minisec.user.view.function;

import com.minisec.common.login.Login;
import com.minisec.user.controller.UserManagementController;
import com.minisec.user.view.details.UserInformationEditView;
import com.minisec.user.view.printer.user.UserInformationPrinter;

import java.util.Scanner;

public class UserManagementView {
    private Scanner sc = new Scanner(System.in);

    private final UserManagementController userManagementController = new UserManagementController();

    public void run(Login user) {
        while (true) {
            System.out.println("""
                    1. 개인정보 조회
                    2. 개인정보 수정
                    3. 보유 금액 충전
                    0. 뒤로가기
                    >> 입력:""");
            int functionNum = Integer.parseInt(sc.nextLine());

            switch (functionNum) {
                case 0: return;
                case 1: selectUserInformation(user); break;
                case 2: updateUserInformation(user); break;
                case 3: updateUserBalance(user); break;
                default:
                    System.out.println("존재하지 않는 기능입니다.");
                    continue;
            }
        }
    }

    private void selectUserInformation(Login user) {
        UserInformationPrinter.print(userManagementController.selectUserByUserId(user));
    }

    private void updateUserInformation(Login user) {
        new UserInformationEditView().run(user);
    }

    private void updateUserBalance(Login user) {
        user = userManagementController.selectUserByUserId(user);

        System.out.printf("현재 잔액 : %,d원\n", user.getUserBalance());
        System.out.println("충전하고 싶은 금액을 입력하세요.");

        userManagementController.chargingBalance(user, sc.nextLine().trim());
    }

}
