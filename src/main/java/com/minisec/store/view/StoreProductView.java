package com.minisec.store.view;
import java.util.Scanner;

public class StoreProductView {
    private Scanner sc = new Scanner(System.in);
    private StoreSelectView storeSelectView = new StoreSelectView();
    private StoreModifyView storeModifyView = new StoreModifyView();

    public void storeProductDetail(int manageId) {

        while (true) {
            System.out.print("""
                    \n======================================
                    ----- 가맹점 상품 관리 ------
                    1. 가맹점 상품 조회
                    2. 가맹점 상품 등록
                    3. 가맹점 상품 수정
                    4. 가맹점 상품 삭제
                    0. 돌아가기
                    ======================================
                    >> 입력: """);

            String productDetailMenu = sc.nextLine();
            switch (productDetailMenu) {
                case "1": storeSelectView.listStoreProductDetail(manageId);break;
                case "2": storeModifyView.insertStoreProductDetail(manageId); break;
                case "3": StoreModifyView.updateStoreProductDetail(manageId); break;
//                case "4": StoreModifyView.deleteStoreProductDetail(manageId); break;
                case "0": return;
                default: System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }
}
