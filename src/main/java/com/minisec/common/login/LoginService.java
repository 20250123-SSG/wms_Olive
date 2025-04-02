package com.minisec.common.login;

import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

import static com.minisec.common.Template.getSqlSession;

public class LoginService {
    public Login login(String userId, String userPwd) {
        SqlSession sqlSession = getSqlSession();
        LoginMapper mapper = sqlSession.getMapper(LoginMapper.class);

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userPwd", userPwd);

        Login userInfo = mapper.selectGetUserInfo(map);

        try {
            if (userInfo == null || !userInfo.getUserPwd().equals(userPwd)) {
                System.out.println("로그인 정보가 잘못되었습니다.");
                return null;
            }
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}