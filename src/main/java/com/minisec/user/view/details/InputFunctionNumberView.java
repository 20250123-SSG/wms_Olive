package com.minisec.user.view.details;

import com.minisec.user.view.printer.ExceptionPrinter;

import java.util.Scanner;

public class InputFunctionNumberView {
    private static final Scanner sc = new Scanner(System.in);

    public static int input() {
        System.out.print(">> 입력:");

        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                ExceptionPrinter.print("기능 번호를 입력해주세요.");
            }
        }
    }

}
