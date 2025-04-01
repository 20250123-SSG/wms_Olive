package com.minisec.common.health;

import org.apache.ibatis.session.SqlSession;

import static com.minisec.common.Template.getSqlSession;

public class HealthCheck {
    public static boolean perform() {
        try (SqlSession session = getSqlSession()) {
            HealthCheckMapper mapper = session.getMapper(HealthCheckMapper.class);
            int result = mapper.healthCheck();
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
