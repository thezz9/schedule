package com.thezz9.writer.repository;

import com.thezz9.writer.entity.Writer;
import com.thezz9.writer.mapper.WriterRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class WriterRepositoryImpl implements WriterRepository {

    private String sql = "";
    private final JdbcTemplate jdbcTemplate;

    public WriterRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *  회원 생성
     *  회원 정보를 DB에 생성하고, 생성된 회원의 ID를 반환
     *  반환된 ID를 사용하여 새로 생성된 회원을 단건 조회하여 최종 반환
     */
    @Override
    public Writer createWriter(Writer writer) {
        sql = "INSERT INTO writer (name, email) VALUES (?, ?)";

        // SQL 쿼리 실행 후 DB에서 자동으로 생성된 기본 키 값을 가져오는 데 사용
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"writer_id"});
            ps.setString(1, writer.getName());
            ps.setString(2, writer.getEmail());
            return ps;
        }, keyHolder);

        // 삽입된 id를 가지고 단건 조회
        Long key = keyHolder.getKey().longValue();
        return getWriterByIdOrElseThrow(key);
    }

    /** 회원 전체 조회 */
    @Override
    public List<Writer> getAllWriters() {
        sql = "SELECT writer_id, name, email, created_at, updated_at FROM writer";

        // 쿼리 실행
        return jdbcTemplate.query(sql, new WriterRowMapper());
    }

    /** 회원 단건 조회
     *  writerId를 기준으로 단건 일정 조회
     *  일치하는 일정이 없으면 null 반환
     */
    @Override
    public Writer getWriterByIdOrElseThrow(Long writerId) {
        sql = "SELECT writer_id, name, email, created_at, updated_at FROM writer where writer_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new WriterRowMapper(), writerId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + writerId + "인 회원이 존재하지 않습니다.");
        }

    }

}
