package com.thezz9.schedule.repository;

import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.mapper.PasswordRowMapper;
import com.thezz9.schedule.mapper.ScheduleRowMapper;
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
    public Schedule createSchedule(Schedule schedule) {
        sql = "INSERT INTO schedule (author_id, task, password) VALUES (?, ?, ?)";

        // SQL 쿼리 실행 후 DB에서 자동으로 생성된 기본 키 값을 가져오는 데 사용
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"schedule_id"});
            ps.setLong(1, schedule.getAuthorId());
            ps.setString(2, schedule.getTask());
            ps.setString(3, schedule.getPassword());
            return ps;
        }, keyHolder);

        // 삽입된 id를 가지고 단건 조회
        Long key = keyHolder.getKey().longValue();
        return getScheduleById(key);
    }

    /** 일정 전체 조회 */
    @Override
    public List<Schedule> getAllSchedules(Long authorId, LocalDate updatedAt) {
        StringBuilder sqlBuilder =
                new StringBuilder("SELECT schedule_id, s.author_id, name, email, task, password, " +
                        "s.created_at,s.updated_at FROM schedule s JOIN author a ON s.author_id = a.author_id WHERE 1=1");

        // 동적으로 추가될 파라미터를 저장하는 리스트
        // 조건에 해당하는 값들이 null이 아닌 경우 이 리스트에 추가
        List<Object> params = new ArrayList<>();

        // authorId 조건이 있을 경우
        if (authorId != null) {
            sqlBuilder.append(" AND s.author_id = ?");
            params.add(authorId);
        }

        // updatedAt 조건이 있을 경우
        if (updatedAt != null) {
            sqlBuilder.append(" AND DATE(s.updated_at) = ?");
            params.add(updatedAt);
        }

        // 수정일 기준 내림차순 정렬
        sqlBuilder.append(" ORDER BY s.updated_at DESC");

        // 최종 SQL 쿼리 생성
        String sql = sqlBuilder.toString();

        // 쿼리 실행
        return jdbcTemplate.query(sql, new ScheduleRowMapper(), params.toArray());
    }

    /** 일정 단건 조회 */
    @Override
    public Schedule getScheduleById(Long scheduleId) {
        sql = "SELECT schedule_id, s.author_id, name, email, task, password, s.created_at,s.updated_at "
            + "FROM schedule s JOIN author a ON s.author_id = a.author_id WHERE schedule_id = ? ORDER BY s.updated_at DESC";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), scheduleId);
    }

    /** 비밀번호 조회 */
    @Override
    public String getPasswordById(Long scheduleId) {
        sql = "SELECT password FROM schedule WHERE schedule_id = ?";
        return jdbcTemplate.queryForObject(sql, new PasswordRowMapper(), scheduleId);
    }

    /** 일정 수정 */
    @Override
    public int updateSchedule(Long scheduleId, String task) {
        sql = "UPDATE schedule SET task = ? WHERE schedule_id = ?";
        return jdbcTemplate.update(sql, task, scheduleId);
    }

    /** 일정 삭제 */
    @Override
    public int deleteSchedule(Long scheduleId) {
        sql = "DELETE FROM schedule WHERE schedule_id = ?";
        return jdbcTemplate.update(sql, scheduleId);
    }

}
