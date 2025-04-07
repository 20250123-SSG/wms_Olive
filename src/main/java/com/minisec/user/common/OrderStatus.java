package com.minisec.user.common;

public enum OrderStatus {
    ORDERABLE("1", "주문가능"),
    OBSERVATION_IMPOSSIBLE("2", "주문불가능");

    private String value;
    private String desc;

    private OrderStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


    public static String getDesc(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getValue().equals(value)) {
                return orderStatus.getDesc();
            }
        }
        return "반려";
    }

}
