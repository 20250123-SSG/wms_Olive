package com.minisec.common.login;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Login {
    private int userId;
    private String userLoginId;
    private String userPwd;
    private String userName;
    private String userPhone;
    private char userType;
    private String userAddress;
    private int userBalance;
}
