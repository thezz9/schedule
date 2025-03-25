package com.thezz9.schedule.mapper;

import com.thezz9.schedule.entity.Schedule;
import com.thezz9.writer.entity.Writer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {

    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Writer 객체는 외부에서 주입받도록 수정
        Writer writer = new Writer(rs.getLong("writer_id"), rs.getString("name"), rs.getString("email"));
        return new Schedule(
                rs.getLong("schedule_id"),
                rs.getString("task"),
                rs.getString("password"),
                rs.getTimestamp("a.created_at").toLocalDateTime(),
                rs.getTimestamp("a.updated_at").toLocalDateTime(),
                writer // 외부에서 생성된 Writer 객체 사용
        );
    }

}
