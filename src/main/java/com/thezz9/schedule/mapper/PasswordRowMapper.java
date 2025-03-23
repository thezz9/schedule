package com.thezz9.schedule.mapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 비밀번호 조회 */
public class PasswordRowMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("password");
    }

}