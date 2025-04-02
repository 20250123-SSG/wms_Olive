package com.minisec.warehouse.model;

import java.util.Random;

public class IncomingDto {
    private int incomingId;
    private int productId;
    private int incomingQuantity;
    private String incomingDate;

    public IncomingDto() {}

    public IncomingDto(int incomingId, int productId, int incomingQuantity, String incomingDate) {
        this.incomingId = incomingId;
        this.productId = productId;
        this.incomingQuantity = incomingQuantity;
        this.incomingDate = incomingDate;
    }

    public int getIncomingId() { return incomingId; }
    public int getProductId() { return productId; }
    public int getIncomingQuantity() { return incomingQuantity; }
    public String getIncomingDate() { return incomingDate; }

    /**
     * 불량품을 제거한 최종 입고 수량을 랜덤으로 결정
     */
    public int getFinalQuantity() {
        Random rand = new Random();
        int min = (int) (incomingQuantity * 0.75); // 최소 75% (3/4)
        int max = incomingQuantity; // 최대 100%
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public String toString() {
        return "입고 ID: " + incomingId + " | 상품 ID: " + productId +
                " | 원래 수량: " + incomingQuantity +
                " | 최종 입고 수량: " + getFinalQuantity() +
                " | 입고 날짜: " + incomingDate;
    }
}
