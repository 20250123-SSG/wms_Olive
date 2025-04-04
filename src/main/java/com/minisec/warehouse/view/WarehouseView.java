package com.minisec.warehouse.view;

import com.minisec.common.login.Login;
import com.minisec.warehouse.controller.WarehouseController;
import com.minisec.warehouse.controller.ShipmentController;
import com.minisec.warehouse.controller.StorageController;
import com.minisec.warehouse.model.dto.ShipmentDto;

import java.util.*;

public class WarehouseView {
    private WarehouseController warehouseController = new WarehouseController();
    private StorageController storageController = new StorageController();
    private ShipmentController wareHouseController = new ShipmentController();
    private Scanner sc = new Scanner(System.in);


    public void mainWarehouseView(Login loginInfo, int manageId) {
        while (true) {
            System.out.print("""
                    \n==================================
                    1. 전체 상품 목록 조회
                    2. 입고 내역 조회
                    3. 발주 요청 확인
                    0. 프로그램 종료
                    ==================================
                    > 입력:""");
            switch (this.sc.nextLine()) {
                case "1":
                    warehouseController.selectAllProducts();
                    break;
                case "2":
                    storageController.selectFilteredStorageList();
                    break;
                case "3":
                    this.selectOrderList(manageId);
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    public void selectOrderList(int manageId) {
        System.out.println("1. 현재 대기중인 발주 목록 확인");
        System.out.println("2. 완료한 발주 목록 확인");
        System.out.println("0. 돌아가기");
        System.out.print("확인하고 싶은 내역을 선택하세요: ");
        int choice = this.sc.nextInt();
        this.sc.nextLine();
        List<ShipmentDto> orders = this.wareHouseController.selectOrderList(manageId, choice);
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println("주문번호\t 주문명\t 주문메모\t 주문상태\t 주문발생일");

        if (choice == 0) {
            System.out.println("상위 메뉴로 돌아갑니다.");
            return;
        }

        for(int i = 0; i < orders.size(); ++i) {
            ShipmentDto order = (ShipmentDto)orders.get(i);
            System.out.printf("%d\t %s\t %s\t %s\t %s \n", i + 1, order.getStoreOrderSubject(), order.getStoreOrderMemo(), this.getOrderStatus(order.getStoreOrderStatus()), order.getCreatedAt());
            map.put(i, order.getStoreOrderId());
        }

        System.out.print("주문의 상세조회를 원하신다면 번호를, 상위메뉴로 돌아갈 경우 0번을 입력하세요: ");
        int orderDetail = this.sc.nextInt();
        this.sc.nextLine(); // 개행 문자 처리

        if (orderDetail == 0) {
            System.out.println("상위 메뉴로 돌아갑니다.");
            return;
        }

        if (!map.containsKey(orderDetail - 1)) {
            System.out.println("잘못된 번호입니다. 다시 시도하세요.");
            return;
        }

        ShipmentDto order = orders.get(orderDetail - 1);
        System.out.println("\n주문 상세 정보");
        System.out.println("주문명: " + order.getStoreOrderSubject());
        System.out.println("주문메모: " + order.getStoreOrderMemo());
        System.out.println("주문상태: " + this.getOrderStatus(order.getStoreOrderStatus()));
        System.out.println("주문발생일: " + order.getCreatedAt());

        if (choice == 1) { // 현재 대기중인 주문이라면
            System.out.print("수주하시겠습니까? (Y/N): ");
            String answer = this.sc.nextLine().trim().toUpperCase();

            if (answer.equals("Y")) {
                System.out.println("수주가 완료되었습니다.");
                // 여기에 수주 처리 로직 추가 가능
            } else {
                System.out.println("돌아갑니다.");
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

}
