package com.minisec.user.view.printer.user;

import com.minisec.common.login.Login;

public class UserInformationPrinter {

    public static void print(Login user){
        System.out.println("\n\n\n========== 개인 정보 ==========");
        System.out.printf("이름 : %s\n", user.getUserName());
        System.out.printf("아이디 : %s\n", user.getUserLoginId());
        System.out.printf("비밀번호 : %s\n", user.getUserPwd());
        System.out.printf("전화번호 : %s\n", user.getUserPhone());
        System.out.printf("주소 : %s\n", user.getUserAddress());
        System.out.printf("보유 잔액 : %s\n", user.getUserBalance());
        System.out.println();
        System.out.println();
    }
}
