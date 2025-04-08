package com.minisec.warehouse.view;

import com.minisec.common.login.Login;
import com.minisec.warehouse.controller.WarehouseController;
import com.minisec.warehouse.controller.ShipmentController;
import com.minisec.warehouse.controller.StorageController;
import com.minisec.warehouse.model.dto.ShipmentDetailDto;
import com.minisec.warehouse.model.dto.ShipmentDto;

import java.util.*;

public class WarehouseView {
    private WarehouseController warehouseController = new WarehouseController();
    private StorageController storageController = new StorageController();
    private ShipmentController shipmentController = new ShipmentController();
    private Scanner sc = new Scanner(System.in);

    // Main view
    public void mainWarehouseView(Login loginInfo, int manageId) {
        while (true) {
            System.out.print("""
                    \n==================================
                    1. 전체 상품 목록 조회
                    2. 입고 내역 조회
                    3. 발주 요청 확인
                    4. 상품 입출고 기록 확인
                    0. 프로그램 종료
                    ==================================
                    > 입력:""");
            switch (this.sc.nextLine()) {
                case "1":
                    warehouseController.selectAllProducts(manageId);
                    break;
                case "2":
                    storageController.selectFilteredStorageList();
                    break;
                case "3":
                    selectOrderList(manageId);
                    break;
                case "4":
//                    selectProductLog(manageId);
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    // 발주 내역 조회
    public void selectOrderList(int manageId) {
        while (true) {
            System.out.println("\n1. 현재 대기중인 발주 목록 확인");
            System.out.println("2. 완료한 발주 목록 확인");
            System.out.println("0. 돌아가기");
            System.out.print("\n메뉴를 선택하세요: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                System.out.println("상위 메뉴로 돌아갑니다.");
                return;
            }

            List<ShipmentDto> orderList = shipmentController.selectOrderList(manageId, choice);
            // 선택값이 1인 경우 수주 여부 확인
            if (choice == 1) {
                System.out.print("주문의 상세조회를 원하신다면 번호를, 상위메뉴로 돌아갈 경우 0번을 입력하세요: ");
                int orderDetail = sc.nextInt();
                sc.nextLine();

                if (orderDetail == 0) {
                    System.out.println("상위 메뉴로 돌아갑니다.");
                    return;
                }
                WarehouseResultView.displayShipmentDetailList(orderList.get(choice - 1));
            }
        }
        /*
            if (choice == 1) { // 현재 대기중인 주문이라면
                System.out.print("수주하시겠습니까? (Y/N): ");
                String answer = sc.nextLine().trim().toUpperCase();

                if (answer.equals("Y")) {
                    boolean result = shipmentController.acceptOrder(order.getStoreOrderId());
                    if (result) {
                        System.out.println("주문이 수주 처리되었습니다.");
                    } else {
                        System.out.println("수주 처리에 실패했습니다.");
                    }
                }
            }
        }

         */
    }


    // 상품별 입출고 로그 조회
    public void selectProductLog(int manageId) {
        // 상품 목록
        Map<Integer, Integer> map = warehouseController.selectAllProducts(manageId);
        System.out.println("입출고 내역을 확인하길 원한다면 번호를 입력, 돌아가기를 원한다면 0번을 입력하세요.");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 0) {
            return;
        }
        int searchProductId = map.get(choice);

        warehouseController.selectSearchProductLog(searchProductId);
    }
}
