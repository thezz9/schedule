package com.thezz9.schedule.mapper;

import com.thezz9.schedule.dto.ScheduleResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  일정 생성 시 등록 정보 조회
 * */
public class ScheduleRowMapperWithPassword implements RowMapper<ScheduleResponseDto> {

    @Override
    public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("details"),
                rs.getString("password"), // 비밀번호 포함
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

}