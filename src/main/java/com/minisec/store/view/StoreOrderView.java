package com.minisec.store.view;
import com.minisec.store.dto.StoreOrderDto;
import com.minisec.store.model.dto.order.*;
import com.minisec.store.service.order.StoreOrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreOrderView {
    private final Scanner sc = new Scanner(System.in);

    public void insertOrderView(int storeId, StoreOrderService storeOrderService) {
        System.out.print("""
                    \n======================================
                                 
                     -----발주 등록 화면-----
                        """);

        System.out.print("""
                    \n======================================
                        """);

        System.out.print("주문 제목: ");
        String storeOrderSubject = sc.nextLine();

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

        System.out.print("주문 메모를 입력하시겠습니까?(Y/N): ");
        String storeOrderMemo = null;
        if ("Y".equalsIgnoreCase(sc.nextLine())) {
            System.out.print("주문 메모: ");
            storeOrderMemo = sc.nextLine();
        }

        storeOrderService.insertOrderWithDetails(storeId, storeOrderSubject, storeOrderMemo, orderDetails);

//        if (isInserted) {
//            System.out.println("발주 등록이 성공했습니다.");
//        } else {
//            System.out.println("발주 등록이 실패했습니다.");
//        }
    }

    public void selectOrderView(int storeId,StoreOrderService storeOrderService) {
        System.out.print("""
                    \n======================================
                                 
                     -----발주 조회 화면-----
                        """);

        List<StoreOrderSelectDto> list = storeOrderService.selectOrderStock(storeId);

        //list.forEach(System.out::println);
        System.out.println();

        for(int i = 0; i<list.size(); i++){
            System.out.print((i+1) + "   ");
            System.out.print("가맹점명:" + list.get(i).getStoreName() + "   ");
            System.out.print("상품명:" + list.get(i).getProductName()  + "   " );
            System.out.print("브랜드명:" + list.get(i).getProductBrandName()  + "   ");
            System.out.print("주문메모:" + list.get(i).getStoreOrderSubject()  + "   ");
            System.out.print("주문수량:" + list.get(i).getStoreOrderDetailQuantity()  + "   ");
            System.out.println();
        }

        System.out.print("""
                \n======================================
                  """);
    }

    public void updateOrderView(int storeId, StoreOrderService storeOrderService) {
        System.out.print("""
                    \n======================================
                                 
                     -----발주 수정 화면-----
                        """);


        List<StoreOrderSelectByUpdateDto> list = storeOrderService.selectStockByUpdate(storeId);
        //list.forEach(System.out :: println);


        for(int i = 0; i<list.size(); i++){
            System.out.print((i+1) + "   ");
            System.out.print("발주번호:" + list.get(i).getStoreOrderDetailId() + "   ");
            System.out.print("주문메모:" + list.get(i).getStoreOrderMemo()+ "   " );
            System.out.print("상품명:" + list.get(i).getProductName() + "   ");
            System.out.print("주문수량:" + list.get(i).getStoreOrderDetailQuantity()  + "   ");
            System.out.println();
        }

        System.out.print("""
                    \n======================================                              
                        """);

        System.out.print("발주번호:");
        int storeOrderDetailId = sc.nextInt();
        sc.nextLine();
        storeOrderService.selectStockByOrder().forEach(System.out::println);

        ProductView(storeOrderService);
        System.out.print("상품 번호를 입력하세요: ");
        int productId = sc.nextInt();
        sc.nextLine();

        System.out.print("수량:");
        int storeOrderDetailQuantity = sc.nextInt();
        sc.nextLine();

        System.out.print("주문제목");
        String storeOrderSubject = sc.nextLine();

        System.out.print("주문메모");
        String storeOrderMemo = sc.nextLine();

        storeOrderService.updateOrderWithDetails(
                storeOrderDetailId, productId, storeOrderDetailQuantity, storeOrderSubject, storeOrderMemo
        );

//        if (isUpdated) {
//            System.out.println("수정완료");
//        } else {
//            System.out.println("수정실패");
//        }
    }

    public void ProductView(StoreOrderService storeOrderService) {
        List<StoreOrderSelectProductByInsertDto> products = storeOrderService.selectStockByOrder();
        //products.forEach(System.out::println);
        System.out.print("""
                    \n======================================
                                 
                     -----등록할 상품 번호를 입력하세요-----
                        """);
        System.out.println();

        for(int i = 0; i<products.size(); i++){
            System.out.print((i+1) + "   ");
            System.out.print("상품번호:" + products.get(i).getProductId() + "   ");
            System.out.print("상품명:" + products.get(i).getProductName() + "   ");
            System.out.print("상품 브랜드명:" + products.get(i).getProductBrandName() + "   ");
            System.out.println();
        }

        System.out.print("""
                    \n======================================
                      """);
    }

}
