package com.minisec.user.view.printer;

public class UpdateStatusPrinter {

    public static void printUpdateUserInfo(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("성공적으로 수정되었습니다.");
        } else {
            System.out.println("수정에 실패하였습니다.");
        }
    }
}
