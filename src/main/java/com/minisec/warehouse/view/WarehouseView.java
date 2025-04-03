package com.minisec.warehouse.view;

import com.minisec.common.login.Login;
import com.minisec.warehouse.controller.ProductController;
import com.minisec.warehouse.controller.StorageController;
import java.util.Scanner;

public class WarehouseView {
    private ProductController productController;
    private StorageController storageController;
    private Scanner sc = new Scanner(System.in);

    public WarehouseView() {
        productController = new ProductController();
        storageController = new StorageController();
    }

    public void mainWarehouseView(Login loginInfo) {
        while (true) {
            System.out.print("""
                    \n==================================
                    1. 전체 상품 목록 조회
                    2. 입고 내역 조회
                    0. 프로그램 종료
                    ==================================
                    > 입력:""");

            String menu = sc.nextLine();
            switch (menu) {
                case "1":
                    productController.selectAllProducts();
                    break;
                case "2":
                    storageController.selectFilteredStorageList();
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }
}
