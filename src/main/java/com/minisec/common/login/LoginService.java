package com.minisec.common.login;

import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

import static com.minisec.common.Template.getSqlSession;

public class LoginService {
    
    // 로그인
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
    
    // 관리 창고 조회
    public int getWareHouseManageId(int userId) {
        SqlSession sqlSession = getSqlSession();
        LoginMapper mapper = sqlSession.getMapper(LoginMapper.class);

        int manageId = mapper.getWareHouseManageId(userId);
        return manageId;
    }
    
    // 관리 가맹점 조회
    public int getStoreManageId(int userId) {
        SqlSession sqlSession = getSqlSession();
        LoginMapper mapper = sqlSession.getMapper(LoginMapper.class);

        int manageId = mapper.getStoreManageId(userId);
        return manageId;
    }
}