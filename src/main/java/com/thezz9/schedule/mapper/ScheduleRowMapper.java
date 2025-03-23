package com.thezz9.schedule.mapper;

import com.thezz9.schedule.entity.Schedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {

    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Schedule(
                rs.getLong("schedule_id"),
                rs.getString("task"),
                rs.getString("password"),
                rs.getLong("author_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("s.created_at").toLocalDateTime(),
                rs.getTimestamp("s.updated_at").toLocalDateTime()
        );
    }
    
}
