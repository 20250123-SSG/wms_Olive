package com.minisec.warehouse.mapper;

import com.minisec.warehouse.model.WareHouseDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class WareHouseMapper {
    private SqlSessionFactory sqlSessionFactory;

    public WareHouseMapper() {
        try {
            String resource = "config/warehouse-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException("MyBatis 설정 파일을 불러오는데 실패했습니다.", e);
        }
    }

    public List<WareHouseDto> getProductList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.minisec.warehouse.mapper.WareHouseMapper.getProductList");
        }
    }
}
