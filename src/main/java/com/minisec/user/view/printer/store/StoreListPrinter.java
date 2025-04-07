package com.minisec.user.view.printer.store;

import com.minisec.user.model.dto.order.StoreDto;

import java.util.List;

public class StoreListPrinter {

    public static void print(List<StoreDto> storeList) {
        System.out.println("========== 가맹점 ==========");

        for (int i = 0; i < storeList.size(); i++) {
            System.out.printf("%d. %s\n"
                    , i + 1
                    , storeList.get(i));
        }
        System.out.println("==========");
    }

}
