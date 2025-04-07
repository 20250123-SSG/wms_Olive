package com.minisec.user.view.printer;

public class InsertStatusPrinter {

    public static void printInsertCartList(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("장바구니에 성공적으로 추가되었습니다.");
        } else {
            System.out.println("장바구니 추가에 실패하였습니다.");
        }
    }

    public static void printInsertOrderList(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("주문에 성공하였습니다.");
        } else {
            System.out.println("주문에 실패했습니다.");
        }
    }

}
