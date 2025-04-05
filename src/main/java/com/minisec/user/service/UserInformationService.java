package com.minisec.user.service;

import com.minisec.common.Template;
import com.minisec.common.login.Login;
import com.minisec.user.model.dao.UserDao;
import com.minisec.user.model.dto.order.UserBalanceUpdateDto;
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

    public int updateUserInformationByFilter(UserInformationEditFilterDto filter) {
        try (SqlSession sqlSession = getSqlSession()) {
            userDao = sqlSession.getMapper(UserDao.class);

            int result = 0;
            try {
                result = userDao.updateUserInformationByFilter(filter); //todo 아이디 중복 예외 처리
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (result != 1) {
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }

    public int updateUserBalance(UserBalanceUpdateDto balanceUpdate) {
        try (SqlSession sqlSession = getSqlSession()) {
            userDao = sqlSession.getMapper(UserDao.class);

            int result = userDao.updateUserBalance(balanceUpdate);
            if (result != 1) {
                sqlSession.rollback();
                return 0;
            }

            sqlSession.commit();
            return 1;
        }
    }

}
