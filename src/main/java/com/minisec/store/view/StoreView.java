package com.minisec.store.view;

import com.minisec.common.login.Login;

import java.util.Scanner;

public class StoreView {
    private Scanner sc = new Scanner(System.in);
    private StoreSelectView storeSelectView = new StoreSelectView();
    private StoreProductView storeProductView = new StoreProductView();

    public void viewStore(Login loginInfo, int manageId) {
        while (true) {
            System.out.print("""
                    \n======================================
                    ----- 가맹점 관리자 ------
                    1. 가맹점 재고 발주
                    2. 가맹점 상품 관리
                    3. 고객 주문 확인
                    0. 로그아웃
                    ========================================
                    >> 입력: """);

            String storeMenu = sc.nextLine();
            switch (storeMenu) {
//                case "1": storeProductOrder(); break;
                case "2":
                    storeProductView.storeProductDetail(manageId);
                    break;
//                case "3": storeCheckUserOrder(); break;
                case "0":
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }

        }
    }

    // 상품 수정 제공 화면

    // 상품 삭제?


    /* 가맹점 상품 조회 viewStoreProduct
       -> 전체조회 ProductAllList
       -> 상품명으로 조회 ProductNameList
       -> 카테고리명으로 조회 ProductCategoryList
       -> 재고 5개 이하 상품만 조회 ProductLimitList
       -> 돌아가기
     */

    /* 가맹점 상품 등록 addStoreProduct
       -> 전체 상품 목록
       -> 상품 선택(상품id로) -> 등록
       -> 이어서 작성하시겠습니까?
       -> N->돌아가기
     */

    /* 가맹점 상품 수정 updateStoreProduct
       -> 가맹점 상품 전체 목록
       -> 상품 수정 -> (storeDto에 적힌 순서)
       -> 이어서 수정하시겠습니까?
       -> N->돌아가기
     */

    /* 가맹점 상품 삭제 deleteStoreProduct
       -> 가맹점 상품 전체 목록
       -> 상품 삭제 -> 삭제할 상품명, 브랜드
       -> 이어서 삭제하시겠습니까?
       -> N->돌아가기
     */


}
