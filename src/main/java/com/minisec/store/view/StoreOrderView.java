package com.minisec.store.view;
import com.minisec.common.login.Login;
import com.minisec.store.dao.StoreOrderMapper;
import com.minisec.store.dto.StoreOrderDetailDto;
import com.minisec.store.dto.StoreOrderDto;
import com.minisec.store.dto.StoreOrderProduct;
import com.minisec.store.dto.StoreOrderSelectDto;
import com.minisec.store.service.StoreOrderService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StoreOrderView {
    private Scanner sc = new Scanner(System.in);
    LocalDateTime currTime = LocalDateTime.now();

    public void orderView(){
        System.out.println("1.발주주문");
        System.out.println("2.발주조회");
        System.out.println("3.발주수정");
        System.out.print(">>입력:");
        int num = sc.nextInt();
        sc.nextLine();
        switch (num){
            case 1:
                insertOrderView();
                break;
            case 2:
                selectOrderView();
                break;
            case 3:
                updateOrderView();
        }
    }



    public void insertOrderView(){
        System.out.println("발주정보를 입력하세요");
        StoreOrderDto storeOrderDto = new StoreOrderDto();
        //창고정보
        storeOrderDto.setWarehouseId(1);
        //가맹점 정보
        storeOrderDto.setStoreId(4);
        System.out.print("주문제목: ");
        String subject = sc.nextLine();
        storeOrderDto.setStoreOrderSubject(subject);
        storeOrderDto.setStoreOrderStatus("1");
        System.out.print("주문메모:");
        String memo = sc.nextLine();
        storeOrderDto.setStoreOrderMemo(memo);
        storeOrderDto.setShipmentDate(currTime);

        int result = new StoreOrderService().insertOrderStock(storeOrderDto);
        System.out.println(result);
        if(result > 0){
            System.out.println("발주 등록 성공");
        }else{
            System.out.println("발주 등록 실패");
        }
        int lastId = new StoreOrderService().getLastInsertId();
        //System.out.println(lastId);

        while (true) {
            StoreOrderDetailDto storeOrderDetailDto = new StoreOrderDetailDto();
            storeOrderDetailDto.setStoreOrderId(lastId);
            //상품수정용
            ProductView();
            System.out.print("상품정보를 입력하세요:(1~9):");
            int productId = sc.nextInt();
            sc.nextLine();
            storeOrderDetailDto.setProductId(productId);
            System.out.print("주문수량: ");
            int quantity = sc.nextInt();
            sc.nextLine();
            storeOrderDetailDto.setStoreOrderDetailQuantity(quantity);

            System.out.print("추가로 등록하시겠습니까?(Y/N): ");
            String input = sc.nextLine();
            int detailResult = new StoreOrderService().insertOrderDetailStock(storeOrderDetailDto);

            if (detailResult > 0) {
                System.out.println("발주 상세 등록 성공");
            } else {
                System.out.println("발주 상세 등록 실패");
            }

            System.out.println("종료하시겠습니까?(Y/N)");
            if (input.toUpperCase().equals("N")) {
                break;
            }
        }
    }
    public void selectOrderView(){
        System.out.println("발주조회화면");
        List<StoreOrderSelectDto> list = new StoreOrderService().selectOrderStock();

       for(StoreOrderSelectDto a : list) {
           System.out.println(a);
       }
    }

    public void updateOrderView() {
        //발주업데이트
        System.out.println("발주수정화면");
        System.out.print("수정할 발주번호를 입력하세요:");
        int storeOrderId = sc.nextInt();
        sc.nextLine();

        //상품수정용
        ProductView();
        System.out.print("수정할 상품번호를 입력하세요(1~9):");
        int productId = sc.nextInt();
        sc.nextLine();
        System.out.print("수정할 수량을 입력하세요:");
        int storeOrderDetailQuantity = sc.nextInt();
        sc.nextLine();
        System.out.print("수정할 주문제목을 입력하세요:");
        String storeOrderSubject = sc.nextLine();
        System.out.print("수정할 주문메모를 입력하세요:");
        String storeOrderMemo = sc.nextLine();

        StoreOrderDto storeOrderDto = new StoreOrderDto();
        storeOrderDto.setStoreOrderId(storeOrderId);
        storeOrderDto.setStoreOrderSubject(storeOrderSubject);
        storeOrderDto.setStoreOrderMemo(storeOrderMemo);
        storeOrderDto.setModifiedAt(currTime);

        int result = new StoreOrderService().updateOrderStock(storeOrderDto);

        if(result > 0) {
            System.out.println("발주 업데이트 성공");
        }else{
            System.out.println("발주 업데이트 실패");
        }

        //발주상세업데이트
        StoreOrderDetailDto storeOrderDetailDto = new StoreOrderDetailDto();

        int storeOrderDetailId = storeOrderId;
        storeOrderDetailDto.setStoreOrderDetailId(storeOrderDetailId);
        storeOrderDetailDto.setStoreOrderId(storeOrderDetailId);
        storeOrderDetailDto.setProductId(productId);
        storeOrderDetailDto.setStoreOrderDetailQuantity(storeOrderDetailQuantity);
        storeOrderDetailDto.setModifiedAt(currTime);

        int detailResult = new StoreOrderService().updateOrderDetailStock(storeOrderDetailDto);

        if(detailResult > 0) {
            System.out.println("발주 상세 업데이트 성공");
        }else{
            System.out.println("발주 상세 업데이트 실패");
        }

    }

    public void ProductView(){
        List<StoreOrderProduct> orderProducts = new StoreOrderService().selectStockByOrder();

        for(StoreOrderProduct a : orderProducts){
            System.out.println(a);
        }
    }
}
