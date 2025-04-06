package com.minisec.store.view;
import com.minisec.store.model.dto.order.*;
import com.minisec.store.service.order.StoreOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreOrderView {
    private final Scanner sc = new Scanner(System.in);

    public void insertOrderView(int storeId, StoreOrderService storeOrderService) {
        System.out.println("발주 정보를 입력하세요");

        System.out.print("주문 제목: ");
        String storeOrderSubject = sc.nextLine();

        System.out.print("주문 메모를 입력하시겠습니까?(Y/N): ");
        String storeOrderMemo = null;
        if ("Y".equalsIgnoreCase(sc.nextLine())) {
            System.out.print("주문 메모: ");
            storeOrderMemo = sc.nextLine();
        }

        List<StoreOrderCommonDetailDto> orderDetails = new ArrayList<>();
        while (true) {
            ProductView(storeOrderService);

            System.out.print("상품 번호를 입력하세요: ");
            int productId = sc.nextInt();
            sc.nextLine();

            System.out.print("주문 수량: ");
            int storeOrderDetailQuantity = sc.nextInt();
            sc.nextLine();

            StoreOrderCommonDetailDto detailDto = new StoreOrderCommonDetailDto();
            detailDto.setProductId(productId);
            detailDto.setStoreOrderDetailQuantity(storeOrderDetailQuantity);
            orderDetails.add(detailDto);

            System.out.print("추가로 등록하시겠습니까? (Y/N): ");
            if ("N".equalsIgnoreCase(sc.nextLine())) {
                break;
            }
        }

        boolean isInserted = storeOrderService.insertOrderWithDetails(storeId, storeOrderSubject, storeOrderMemo, orderDetails);

        if (isInserted) {
            System.out.println("발주 등록이 성공했습니다.");
        } else {
            System.out.println("발주 등록이 실패했습니다.");
        }
    }

    public void selectOrderView(StoreOrderService storeOrderService) {
        System.out.println("발주 조회 화면");
        List<StoreOrderSelectDto> list = storeOrderService.selectOrderStock();
        list.forEach(System.out::println);
    }

    public void updateOrderView(int storeId, StoreOrderService storeOrderService) {
        System.out.println("발주 수정 화면");
        storeOrderService.selectStockByUpdate(storeId).forEach(System.out::println);

        System.out.print("수정할 발주 번호를 입력하세요: ");
        int storeOrderDetailId = sc.nextInt();
        sc.nextLine();

        storeOrderService.selectStockByOrder().forEach(System.out::println);

        System.out.print("수정할 상품 번호를 입력하세요: ");
        int productId = sc.nextInt();
        sc.nextLine();

        System.out.print("수정할 수량을 입력하세요: ");
        int storeOrderDetailQuantity = sc.nextInt();
        sc.nextLine();

        System.out.print("수정할 주문 제목을 입력하세요: ");
        String storeOrderSubject = sc.nextLine();

        System.out.print("수정할 주문 메모를 입력하세요: ");
        String storeOrderMemo = sc.nextLine();

        boolean isUpdated = storeOrderService.updateOrderWithDetails(
                storeOrderDetailId, productId, storeOrderDetailQuantity, storeOrderSubject, storeOrderMemo
        );

        if (isUpdated) {
            System.out.println("발주 업데이트 성공");
        } else {
            System.out.println("발주 업데이트 실패");
        }
    }

    public void ProductView(StoreOrderService storeOrderService) {
        List<StoreOrderSelectProductByInsertDto> products = storeOrderService.selectStockByOrder();
        products.forEach(System.out::println);
    }

}
