package com.minisec.user.model.dto.user;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserInformationEditFilterDto {

    private int userId;

    private String userLoginId;
    private String userPwd;
    private String userName;
    private String userPhone;
    private String userAddress;

}
