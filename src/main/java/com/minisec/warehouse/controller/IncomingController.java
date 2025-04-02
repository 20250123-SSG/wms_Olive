package com.minisec.warehouse.controller;

import com.minisec.warehouse.mapper.IncomingMapper;
import com.minisec.warehouse.model.IncomingDto;

import java.util.List;

public class IncomingController {
    private IncomingMapper mapper;

    public IncomingController() {
        mapper = new IncomingMapper();
    }

    public void selectIncomingList() {
        List<IncomingDto> incomingList = mapper.getIncomingList();
        System.out.println("\n📦 입고 내역 조회 📦");

        for (IncomingDto incoming : incomingList) {
            System.out.println("\n🔍 불량품 검사중...");
            System.out.println(incoming);
        }
    }
}
