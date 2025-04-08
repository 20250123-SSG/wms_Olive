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
            System.out.print("\n확인하고 싶은 내역을 선택하세요: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                System.out.println("상위 메뉴로 돌아갑니다.");
                return;
            }

            Map<Integer, Integer> map = shipmentController.selectOrderList(manageId, choice);
            // 선택값이 1인 경우 수주 여부 확인
            if (choice == 1){
                System.out.print("주문의 상세조회를 원하신다면 번호를, 상위메뉴로 돌아갈 경우 0번을 입력하세요: ");
                int orderDetail = sc.nextInt();
            }
        }
    }
/*
//        List<ShipmentDto> orders = shipmentController.selectOrderList(manageId, choice);
//        Map<Integer, Integer> map = new HashMap<>();
        System.out.println("\n────────────────────────────────────────────────────────────────────────────────");
        System.out.println("주문번호\t 주문명\t\t\t 주문메모\t 주문상태\t 주문발생일");

        for (int i = 0; i < orders.size(); ++i) {
            ShipmentDto order = orders.get(i);
            System.out.printf("%d\t\t\t %s\t %s\t\t %s\t\t %s \n", i + 1, order.getStoreOrderSubject(), order.getStoreOrderMemo(), this.getOrderStatus(order.getStoreOrderStatus()), order.getCreatedAt());
            map.put(i, order.getStoreOrderId());
        }
        System.out.println("────────────────────────────────────────────────────────────────────────────────\n");

        System.out.print("주문의 상세조회를 원하신다면 번호를, 상위메뉴로 돌아갈 경우 0번을 입력하세요: ");
        int orderDetail = sc.nextInt();
        sc.nextLine();

        if (orderDetail == 0) {
            System.out.println("상위 메뉴로 돌아갑니다.");
            continue;
        }

        if (!map.containsKey(orderDetail - 1)) {
            System.out.println("잘못된 번호입니다. 다시 시도하세요.");
            continue;
        }

        ShipmentDto order = orders.get(orderDetail - 1);
        System.out.println("────────────────────────────────────────────────────────────────────────────────\n");
        System.out.println("[주문 상세 정보]");
        System.out.println("주문명: " + order.getStoreOrderSubject());
        System.out.println("주문메모: " + order.getStoreOrderMemo());
        System.out.println("주문상태: " + getOrderStatus(order.getStoreOrderStatus()));
        System.out.println("주문발생일: " + order.getCreatedAt());

        System.out.println("\n[주문 상세 항목]");
        for (ShipmentDetailDto detail : order.getOrderDetails()) {
            System.out.printf("상품명: %s, 수량: %d, 생성일: %s\n",
                    detail.getProductName(),
                    detail.getStoreOrderDetailQuantity(),
                    detail.getCreatedAt());

            System.out.println("────────────────────────────────────────────────────────────────────────────────\n");

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
    }
    }

    public String getOrderStatus(char status) {
        String orderStatus = "";
        if (status == '1') {
            orderStatus = "대기";
        } else if (status == '2') {
            orderStatus = "수주";
        } else if (status == '3') {
            orderStatus = "거절";
        } else {
            orderStatus = "완료";
        }

        return orderStatus;
    }

    // 상품별 입출고 로그 조회
    public void selectProductLog(int manageId) {
        // 상품 목록
        Map<Integer, Integer> map = warehouseController.selectAllProducts(manageId);
        System.out.println("입출고 내역을 확인하길 원한다면 번호를 입력, 돌아가기를 원한다면 0번을 입력하세요.");
        int choice = sc.nextInt();
        sc.nextLine();
        if(choice == 0){
            return;
        }
        int searchProductId = map.get(choice);

        warehouseController.selectSearchProductLog(searchProductId);
    }

 */

    }