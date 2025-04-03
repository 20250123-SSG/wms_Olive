package com.minisec.user.view;

import com.minisec.common.login.Login;
import com.minisec.user.view.function.CartView;
import com.minisec.user.view.function.OrderHistoryView;
import com.minisec.user.view.function.OrderView;
import com.minisec.user.view.function.UserManagementView;

import java.util.Scanner;

public class UserMainView {
    private Scanner sc = new Scanner(System.in);

    public void run(Login loginInfo) {
        while(true){
            System.out.println("""
                1. 개인 정보 관리
                2. 장바구니 관리
                3. 구매
                4. 구매 내역 조회
                0. 종료하기
                >> 입력 :""");
            int functionNum = Integer.parseInt(sc.nextLine());


            if(functionNum == 0){
                System.out.println("프로그램을 종료합니다.");
                return;
            }
            switch(functionNum){
                case 1: new UserManagementView().run(loginInfo); break;
                case 2: new CartView().run(loginInfo); break;
                case 3: new OrderView().run(loginInfo); break;
                case 4: new OrderHistoryView().run(loginInfo); break;
                default:
                    System.out.println("존재하지 않는 기능입니다.");
                    continue;
            }
        }
    }

}
