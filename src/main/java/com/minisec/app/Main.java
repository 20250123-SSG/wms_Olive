package com.minisec.app;

import java.util.Scanner;

import com.minisec.common.health.HealthCheck;
import com.minisec.common.login.Login;
import com.minisec.common.login.LoginService;
import com.minisec.store.view.StoreView;
import com.minisec.user.view.UserMainView;
import com.minisec.warehouse.view.WareHouseView;


public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();

        Scanner sc = new Scanner(System.in);

        System.out.println("올리브 창고 시스템 연결 시도중 ...");

        // DB 연결상태 확인
        boolean healCheck = HealthCheck.perform();
        if (!healCheck) {
            System.err.println("DB 연결 검사가 실패하였습니다. DB 상태를 확인 후 프로그램을 재시작해주시기 바랍니다.");
            System.exit(1);
        }
        System.out.println("DB 연결 검사가 정상적으로 실행되었습니다.");

        System.out.println("""
                =========================================
                 #####   #        ###  #     #  #######\s
                #     #  #         #   #     #  #      \s
                #     #  #         #   #     #  #      \s
                #     #  #         #   #     #  #####  \s
                #     #  #         #    #   #   #      \s
                #     #  #         #     # #    #      \s
                 #####   #######  ###     #     #######\s
                ==========================================
                """);

        System.out.print("아이디를 입력하세요: ");
        String userId = sc.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String userPwd = sc.nextLine();

        Login loginInfo = loginService.login(userId, userPwd);
        char userType = loginInfo.getUserType();
        if (userType == 'W') {
            System.out.println("창고관리자");
            WareHouseView wareHouseView = new WareHouseView();
            wareHouseView.mainWareHouseView(loginInfo);
        } else if (userType == 'S') {
            System.out.println("매장관리자");
            StoreView storeView = new StoreView();
            storeView.viewStore(loginInfo);
        } else{
            System.out.println("일반유저");
            UserMainView userMainView = new UserMainView();
            userMainView.run(loginInfo);
        }
    }
}