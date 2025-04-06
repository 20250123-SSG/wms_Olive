package com.minisec.user.view.printer;

public class UpdateStatusPrinter {

    public static void printUpdateUserInfo(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("성공적으로 수정되었습니다.");
        } else {
            System.out.println("수정에 실패하였습니다.");
        }
    }

    public static void printUpdateBalanceInfo(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("잔액이 충전되었습니다.");
        } else {
            System.out.println("충전에 실패하였습니다.");
        }
    }

    public static void printUpdateCartQuantity(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("수량이 변경되었습니다.");
        } else {
            System.out.println("수량 변경에 실패하였습니다.");
        }
    }

}
