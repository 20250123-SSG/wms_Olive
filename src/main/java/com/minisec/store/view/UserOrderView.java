package com.minisec.store.view;

import com.minisec.store.model.dto.user.StoreUserSelectDto;
import com.minisec.store.service.user.StoreUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserOrderView {
    private final Scanner sc = new Scanner(System.in);

    public void selectOrderView(int storeId,StoreUserService storeUserService){

        System.out.print("""
                    \n======================================
                                 
                     -----고객 주문 승인상태-----
                        """);
        List<StoreUserSelectDto> list = storeUserService.selectStoreUserStock(storeId);

        for(int i = 0; i<list.size(); i++){
            System.out.print((i+1) + "   ");
            System.out.print("고객 주문번호:" + list.get(i).getUserOrderDetailId() + "   ");
            System.out.print("고객명:" + list.get(i).getUserName() + "   ");
            System.out.print("가맹점명:" + list.get(i).getStoreName()+ "   " );
            System.out.print("상품명:" + list.get(i).getProductName() + "   ");
            System.out.print("브랜드명:" + list.get(i).getProductBrandName()  + "   ");
            System.out.print("주문수량:" + list.get(i).getUserOrderDetailQuantity()  + "   ");
            System.out.print("주문메모:" + list.get(i).getUserOrderMemo()  + "   ");

            //급하게 승인완료 처리 -> DB변경 X 콘솔상에서만
            String userOrderStatus = list.get(i).getUserOrderStatus();
            if ("1".equals(userOrderStatus) || "Y".equals(userOrderStatus)) {
                userOrderStatus = "승인대기";
            } else if ("2".equals(userOrderStatus)) {
                userOrderStatus = "승인거부";
            } else {
                userOrderStatus = "승인완료"; // 기본값
            }
            System.out.println("승인상태:" + userOrderStatus + "   ");
            System.out.println();
        }

        System.out.print("""
                \n======================================
                  """);
    }

    public void selectOrderByOrderStatusPendingView(int storeId,StoreUserService storeUserService){

        System.out.print("""
                    \n======================================
                                 
                     -----승인 대기중인 주문-----
                        """);
        List<StoreUserSelectDto> list = storeUserService.selectStoreUserStockWithStatus(storeId);

        for(int i = 0; i<list.size(); i++){
            System.out.print((i+1) + "   ");
            System.out.print("고객 주문번호:" + list.get(i).getUserOrderDetailId() + "   ");
            System.out.print("고객명:" + list.get(i).getUserName() + "   ");
            System.out.print("가맹점명:" + list.get(i).getStoreName()+ "   " );
            System.out.print("상품명:" + list.get(i).getProductName() + "   ");
            System.out.print("브랜드명:" + list.get(i).getProductBrandName()  + "   ");
            System.out.print("주문수량:" + list.get(i).getUserOrderDetailQuantity()  + "   ");
            System.out.print("주문메모:" + list.get(i).getUserOrderMemo()  + "   ");

            //급하게 승인완료 처리 -> DB변경 X 콘솔상에서만
            String userOrderStatus = list.get(i).getUserOrderStatus();
            if ("1".equals(userOrderStatus) || "Y".equals(userOrderStatus)) {
                userOrderStatus = "승인대기";
            } else if ("2".equals(userOrderStatus)) {
                userOrderStatus = "승인거부";
            } else {
                userOrderStatus = "승인완료"; // 기본값
            }
            System.out.println("승인상태:" + userOrderStatus + "   ");
            System.out.println();
        }

        System.out.print("""
                \n======================================
                  """);
    }

    public void updateOrderStatusView(int storeId,StoreUserService storeUserService){
        List<StoreUserSelectDto> list = storeUserService.selectStoreUserStock(storeId);
        

        System.out.print("고객 주문번호를 입력하세요: ");
        int userOrderDetailId = sc.nextInt();
        sc.nextLine();

        System.out.print("승인확인(1:승인확인,2:승인취소):");
        String newStatus = sc.nextLine();
        if (newStatus.equals("1")) {
            //쿼리짜기 싫어!!!!!!!!
           for(StoreUserSelectDto a : list){
               if(a.getUserOrderDetailId() == userOrderDetailId){
                   a.setUserOrderStatus("승인완료");
                   break;
               }
           }
            System.out.print("""
                    \n======================================
                                 
                     -----승인 완료----
                        """);

            for(int i = 0; i<list.size(); i++){
                System.out.print((i+1) + "   ");
                System.out.print("고객 주문번호:" + list.get(i).getUserOrderDetailId() + "   ");
                System.out.print("고객명:" + list.get(i).getUserName() + "   ");
                System.out.print("가맹점명:" + list.get(i).getStoreName()+ "   " );
                System.out.print("상품명:" + list.get(i).getProductName() + "   ");
                System.out.print("브랜드명:" + list.get(i).getProductBrandName()  + "   ");
                System.out.print("주문수량:" + list.get(i).getUserOrderDetailQuantity()  + "   ");
                System.out.print("주문메모:" + list.get(i).getUserOrderMemo()  + "   ");
                String userOrderStatus = list.get(i).getUserOrderStatus();
                if ("1".equals(userOrderStatus) || "Y".equals(userOrderStatus)) {
                    userOrderStatus = "승인대기";
                } else if ("2".equals(userOrderStatus)) {
                    userOrderStatus = "승인거부";
                }
                System.out.println("승인상태:" + userOrderStatus + "   ");
                System.out.println();

            }
            System.out.print("""
                \n======================================
                  """);
        }


        if (newStatus.equals("2")) {
            System.out.println("거절사유를 입력하세요(1.판매중단):");
            int num = sc.nextInt();
            sc.nextLine();
            if(num == 1){
                storeUserService.updateUserOrderStatusByDetailId(userOrderDetailId, newStatus);

//                if (isUpdated) {
//                    System.out.println("수정완료");
//                } else {
//                    System.out.println("수정실패");
//                }
            }
        }
    }
}
