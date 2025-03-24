package com.thezz9.writer.mapper;

import com.thezz9.writer.entity.Writer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WriterRowMapper implements RowMapper<Writer> {

    @Override
    public Writer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Writer(
                rs.getLong("writer_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
    
}
