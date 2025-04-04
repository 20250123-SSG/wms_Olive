package com.minisec.user.view.details;

import com.minisec.common.login.Login;
import com.minisec.user.model.dto.order.OrderDto;
import com.minisec.user.model.dto.order.OrderProductDto;
import com.minisec.user.model.dto.order.StoreDto;
import com.minisec.user.model.helper.OrderAssembler;
import com.minisec.user.view.printer.OrderDetailsPrinter;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputOrderMemoView {
    private final Scanner sc = new Scanner(System.in);

    public List<OrderDto> run(Login user, Map<StoreDto, List<OrderProductDto>> orderListByStore) {
        List<OrderDto> orderList = new OrderAssembler().getOrderList(user, orderListByStore);

        for (OrderDto order : orderList) {
            OrderDetailsPrinter.printOne(order);
            System.out.println("[ 주문 메모를 입력해주세요. (추가를 원하지 않으시면 공백을 입력하세요.) ]");
            System.out.println(">> 입력:");
            String inputOrderMemo = sc.nextLine().trim();

            if (inputOrderMemo.isEmpty()) {
                continue;
            }
            order.setOrderMemo(inputOrderMemo);
            System.out.println("저장되었습니다.");
        }

        return orderList;
    }

}
