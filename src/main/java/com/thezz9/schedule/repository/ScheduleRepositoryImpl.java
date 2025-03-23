package com.thezz9.schedule.repository;

import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.mapper.ScheduleRowMapperWithPassword;
import com.thezz9.schedule.mapper.ScheduleRowMapperWithoutPassword;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepositoryImpl implements  ScheduleRepository {

    private String sql = "";
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *  일정 생성
     *  단건 조회 메서드 getScheduleById() 실행 시
     *  boolean includePassword 값 false -> 비밀번호 포함 조회
     * */
    @Override
    public ScheduleResponseDto createSchedule(Schedule schedule) {
        sql = "INSERT INTO schedule (title, author, details, password) VALUES (?, ?, ?, ?)";

        // SQL 쿼리 실행 후 DB에서 자동으로 생성된 기본 키 값을 가져오는 데 사용
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, schedule.getTitle());
            ps.setString(2, schedule.getAuthor());
            ps.setString(3, schedule.getDetails());
            ps.setString(4, schedule.getPassword());
            return ps;
        }, keyHolder);

        // 삽입된 id를 가지고 단건 조회
        Long key = keyHolder.getKey().longValue();
        return getScheduleById(key, false);
    }

    /** 일정 전체 조회 */
    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        sql = "SELECT * FROM schedule ORDER BY updated_at DESC";
        return jdbcTemplate.query(sql, new ScheduleRowMapperWithoutPassword());
    }

    /** 일정 단건 조회 */
    @Override
    public ScheduleResponseDto getScheduleById(Long id, boolean includePassword) {
        sql = "SELECT * FROM schedule WHERE id = ? ORDER BY updated_at DESC";

        // 비밀번호를 포함한 조회와 제외한 조회를 조건으로 분기
        if (includePassword) {
            return jdbcTemplate.queryForObject(sql, new ScheduleRowMapperWithoutPassword(), id);
        } else {
            return jdbcTemplate.queryForObject(sql, new ScheduleRowMapperWithPassword(), id);
        }
    }

    /** 일정 조건 조회 */
    @Override
    public List<ScheduleResponseDto> getSchedulesByCondition(String author, LocalDate updatedAt) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM schedule WHERE 1=1");

        // 동적으로 추가될 파라미터를 저장하는 리스트
        // 조건에 해당하는 값들이 null이 아닌 경우 이 리스트에 추가
        List<Object> params = new ArrayList<>();

        // author 조건이 있을 경우
        if (author != null) {
            sqlBuilder.append(" AND author = ?");
            params.add(author);
        }

        // updatedAt 조건이 있을 경우
        if (updatedAt != null) {
            sqlBuilder.append(" AND DATE(updated_at) = ?");
            params.add(updatedAt);
        }

        // 수정일 기준 내림차순 정렬
        sqlBuilder.append(" ORDER BY updated_at DESC");

        // 최종 SQL 쿼리 생성
        String sql = sqlBuilder.toString();

        // 쿼리 실행
        return jdbcTemplate.query(sql, new ScheduleRowMapperWithoutPassword(), params.toArray());
    }

    @Override
    public ScheduleResponseDto getPasswordById(Long id) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String author, String details, String password) {
        return null;
    }

    @Override
    public void deleteSchedule(Long id, String password) {

    }

}