package com.minisec.app;

import java.util.Scanner;

import com.minisec.common.health.HealthCheck;
import com.minisec.common.login.Login;
import com.minisec.common.login.LoginService;
import com.minisec.store.view.StoreView;
import com.minisec.user.view.UserMainView;
import com.minisec.warehouse.view.WarehouseView;


public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();

        Scanner sc = new Scanner(System.in);

        System.out.println("올리브영 창고 시스템 연결 시도중 ...");

        // DB 연결상태 확인
        boolean healCheck = HealthCheck.perform();
        if (!healCheck) {
            System.err.println("DB 연결 검사가 실패하였습니다. DB 상태를 확인 후 프로그램을 재시작해주시기 바랍니다.");
            System.exit(1);
        }
        System.out.println("DB 연결 검사가 정상적으로 실행되었습니다.");

        System.out.println("""
                ========================================
                 #####   #        ###  #     #  #######\s
                #     #  #         #   #     #  #      \s
                #     #  #         #   #     #  #      \s
                #     #  #         #   #     #  #####  \s
                #     #  #         #    #   #   #      \s
                #     #  #         #     # #    #      \s
                 #####   #######  ###     #     #######\s
                =========================================
                """);

        System.out.print("아이디를 입력하세요: ");
        String userLoginId = sc.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String userPwd = sc.nextLine();

        // 로그인
        Login loginInfo = loginService.login(userLoginId, userPwd);
        char userType = loginInfo.getUserType();
        int userId = loginInfo.getUserId();

        // 유저인 경우 그대로 로그인 정보를, 그외의 경우 중간 테이블에서 관리지점 확인
        if (userType == 'C') {
            System.out.println("일반유저");
            UserMainView userMainView = new UserMainView();
            userMainView.run(loginInfo);
        } else {
            if (userType == 'W') {
                int manageId = loginService.getWareHouseManageId(userId);
                WarehouseView warehouseView = new WarehouseView();
                warehouseView.mainWarehouseView(loginInfo,manageId);
            }
            if (userType == 'S') {
                int manageId = loginService.getStoreManageId(userId);
                StoreView storeView = new StoreView();
                storeView.viewStore(loginInfo,manageId);
            }
        }
    }
}