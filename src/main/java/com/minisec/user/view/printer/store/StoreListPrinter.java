package com.minisec.user.view.printer.store;

import com.minisec.user.model.dto.store.StoreDto;

import java.util.List;
import java.util.Map;

public class StoreListPrinter {

    private final static String STORE_DETAILS = "%d. %s\n";

    public static void print(Map<Integer, StoreDto> storeListByUniqueNumber) {
        System.out.println("================== 가맹점 ==================");

        for (Map.Entry<Integer, StoreDto> entry : storeListByUniqueNumber.entrySet()) {
            int uniqueNumber = entry.getKey();
            StoreDto storeDto = entry.getValue();

            System.out.printf(STORE_DETAILS, uniqueNumber, storeDto.getStoreName());
        }
        System.out.println("============================================\n");
    }

}
