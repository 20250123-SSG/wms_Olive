package com.minisec.warehouse.view;

import com.minisec.warehouse.controller.WareHouseController;
import com.minisec.warehouse.controller.IncomingController;
import java.util.Scanner;

public class WareHouseView {
    private WareHouseController wareHouseController;
    private IncomingController incomingController;
    private Scanner sc;

    public WareHouseView() {
        wareHouseController = new WareHouseController();
        incomingController = new IncomingController();
        sc = new Scanner(System.in);
    }

    public void mainWareHouseView(){
        try {
            while(true){
                System.out.print("""
                        \n==================================
                        1. 전체 상품 목록 조회
                        2. 입고 내역 조회
                        0. 프로그램 종료
                        ==================================
                        > 입력: """);

                String menu = sc.nextLine();
                switch(menu){
                    case "1": wareHouseController.selectProductList(); break;
                    case "2": incomingController.selectIncomingList(); break;
                    case "0":
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                }
            }
        } finally {
            sc.close();
        }
    }

    public static void main(String[] args) {
        WareHouseView view = new WareHouseView();
        view.mainWareHouseView();
    }
}
