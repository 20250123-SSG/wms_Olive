package com.minisec.user.view.printer.store;

import com.minisec.user.model.dto.order.StoreDto;

import java.util.List;

public class StoreListPrinter {

    private final static String STORE_DETAILS = "%d. %s\n";

    public static void print(List<StoreDto> storeList) {
        System.out.println("================== 가맹점 ==================");

        for (int i = 0; i < storeList.size(); i++) {
            System.out.printf(STORE_DETAILS
                    , i + 1
                    , storeList.get(i).getStoreName()
            );
        }
        System.out.println("============================================\n");
    }

}
