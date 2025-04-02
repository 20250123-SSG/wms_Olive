package com.minisec.common.login;

import java.util.Map;

public interface LoginMapper {
    Login selectGetUserInfo(Map<String, String> params);
}
