package com.minisec.store.view;

import com.minisec.store.model.dto.order.StoreOrderSelectDto;
import com.minisec.store.model.dto.user.StoreUserSelectDto;
import com.minisec.store.service.user.StoreUserService;


import java.util.List;
import java.util.Scanner;

public class UserOrderView {
    private final Scanner sc = new Scanner(System.in);

    public void selectOrderView(int storeId,StoreUserService storeUserService){
        System.out.println("고객주문조회화면");
        List<StoreUserSelectDto> storeUserSelectDto = new StoreUserService().selectStoreUserStock(storeId);
        storeUserSelectDto.forEach(System.out::println);
    }

    public void updateOrderStatusView(int storeId,StoreUserService storeUserService){
        //임시구현
        List<StoreUserSelectDto> storeUserSelectDto = new StoreUserService().selectStoreUserStock(storeId);
        storeUserSelectDto.forEach(System.out::println);

        System.out.print("고객 주문번호를 입력하세요: ");
        int userOrderDetailId = sc.nextInt();
        sc.nextLine();

        System.out.print("승인확인(Y:승인확인,N:승인취소)");
        String newStatus = sc.nextLine();

        boolean isUpdated = new StoreUserService().updateUserOrderStatusByDetailId(userOrderDetailId, newStatus);

        if (isUpdated) {
            System.out.println("주문 상태가 성공적으로 업데이트되었습니다.");
        } else {
            System.out.println("주문 상태 업데이트에 실패했습니다.");
        }

    }
}
