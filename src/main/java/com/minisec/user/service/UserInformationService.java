package com.minisec.user.service;

import com.minisec.common.login.Login;
import com.minisec.user.model.dao.user.UserDao;
import com.minisec.user.model.dto.user.UserBalanceUpdateDto;
import com.minisec.user.model.dto.user.UserInformationEditFilterDto;
import org.apache.ibatis.session.SqlSession;

import static com.minisec.common.Template.getSqlSession;

public class UserInformationService {

    private UserDao userDao;


    public Login selectUserByUserId(int userId) {
        try (SqlSession sqlSession = getSqlSession()) {
            userDao = sqlSession.getMapper(UserDao.class);

            return userDao.selectUserByUserId(userId);
        }
    }


    public void updateUserInformationByFilter(UserInformationEditFilterDto filter) {
        try (SqlSession sqlSession = getSqlSession()) {
            userDao = sqlSession.getMapper(UserDao.class);

            int result = userDao.updateUserInformationByFilter(filter);
            if (result != 1) {
                sqlSession.rollback();
                throw new IllegalArgumentException("개인정보 수정에 실패하였습니다.");
            }

            sqlSession.commit();
        }
    }


    public void updateUserBalance(UserBalanceUpdateDto balanceUpdate) {
        try (SqlSession sqlSession = getSqlSession()) {
            userDao = sqlSession.getMapper(UserDao.class);

            int result = userDao.updateUserBalance(balanceUpdate);
            if (result != 1) {
                sqlSession.rollback();
                throw new IllegalArgumentException("잔액 충전에 실패하였습니다.");
            }

            sqlSession.commit();
        }
    }

}
