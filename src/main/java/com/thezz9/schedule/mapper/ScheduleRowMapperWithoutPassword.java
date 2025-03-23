package com.thezz9.schedule.mapper;

import com.thezz9.schedule.dto.ScheduleResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  조회 시 비밀번호 숨김 처리
 * */
public class ScheduleRowMapperWithoutPassword implements RowMapper<ScheduleResponseDto> {

    @Override
    public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("details"),
                "*".repeat(rs.getString("password").length()),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

}