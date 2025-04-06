package com.minisec.user.view.printer;

public class DeleteStatusPrinter {

    public static void printDeleteCart(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("성공적으로 삭제되었습니다.");
        } else {
            System.out.println("실패하였습니다.");
        }
    }

}
