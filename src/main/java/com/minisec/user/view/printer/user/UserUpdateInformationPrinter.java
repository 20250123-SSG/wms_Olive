package com.minisec.user.view.printer.user;

public class UserUpdateInformationPrinter {

    public static void print(String before, String after) {
        System.out.printf("수정 전 : %s\n", before);
        System.out.printf("수정 후 : %s\n", after);
    }
}
