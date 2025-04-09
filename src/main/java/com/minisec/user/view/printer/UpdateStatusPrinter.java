package com.minisec.user.view.printer;

public class UpdateStatusPrinter {

    public static void printUpdateUserInfo(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\n\n성공적으로 수정되었습니다.\n\n");
        } else {
            System.out.println("\n\n수정에 실패하였습니다.\n\n");
        }
    }

    public static void printUpdateBalanceInfo(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\n\n잔액이 충전되었습니다.\n\n");
        } else {
            System.out.println("\n\n충전에 실패하였습니다.\n\n");
        }
    }

    public static void printUpdateCartQuantity(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\n\n수량이 변경되었습니다.\n\n");
        } else {
            System.out.println("\n\n수량 변경에 실패하였습니다.\n\n");
        }
    }

}
