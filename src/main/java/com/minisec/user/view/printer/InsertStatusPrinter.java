package com.minisec.user.view.printer;

public class InsertStatusPrinter {

    public static void printInsertCartList(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\n\n장바구니에 성공적으로 추가되었습니다.\n\n");
        } else {
            System.out.println("\n\n장바구니 추가에 실패하였습니다.\n\n");
        }
    }

    public static void printInsertOrderList(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\n\n주문에 성공하였습니다.\n\n");
        } else {
            System.out.println("\n\n주문에 실패했습니다.\n\n");
        }
    }

}
