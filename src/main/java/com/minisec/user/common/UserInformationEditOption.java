package com.minisec.user.common;

public enum UserInformationEditOption {
    ID("아이디"),
    PASSWORD("비밀번호"),
    NAME("이름"),
    PHONE("전화번호"),
    ADDRESS("주소")
    ;
    private String value;

    UserInformationEditOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
