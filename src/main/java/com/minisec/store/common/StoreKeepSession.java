package com.minisec.store.common;

import com.minisec.common.login.Login;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreKeepSession {

    private static Login login;

    private static int storeManageId;

    public static void setSession(Login loginInfo, int manageId) {
        login = loginInfo;
        storeManageId = manageId;
    }

    public static Login getLogin() {
        return login;
    }

    public static int getStoreManageId() {
        return storeManageId;
    }
}