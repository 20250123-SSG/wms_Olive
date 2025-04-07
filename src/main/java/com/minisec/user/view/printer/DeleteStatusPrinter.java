package com.minisec.user.view.printer;

public class DeleteStatusPrinter {

    public static void printDeleteCart(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("\n\n성공적으로 삭제되었습니다.\n\n");
        } else {
            System.out.println("\n\n삭제에 실패하였습니다.\n\n");
        }
    }

}
