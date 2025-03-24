package com.thezz9.schedule.repository;

import com.thezz9.schedule.dto.Paging;
import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.mapper.PasswordRowMapper;
import com.thezz9.schedule.mapper.ScheduleRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     *  일정 정보를 DB에 생성하고, 생성된 일정의 ID를 반환
     *  반환된 ID를 사용하여 새로 생성된 일정을 단건 조회하여 최종 반환
     */
    @Override
    public Schedule createSchedule(Schedule schedule) {
        sql = "INSERT INTO schedule (writer_id, task, password) VALUES (?, ?, ?)";

        // SQL 쿼리 실행 후 DB에서 자동으로 생성된 기본 키 값을 가져오는 데 사용
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"schedule_id"});
            ps.setLong(1, schedule.getWriter().getWriterId());
            ps.setString(2, schedule.getTask());
            ps.setString(3, schedule.getPassword());
            return ps;
        }, keyHolder);

        // 삽입된 id를 가지고 단건 조회
        Long key = keyHolder.getKey().longValue();
        return getScheduleByIdOrElseThrow(key);
    }

    /** 일정 전체 조회
     *  writerId와 updatedAt에 해당하는 일정들을 조회
     *  소프트 딜리트 처리된 일정은 조회 되지 않음
     *  페이징(paging) 처리를 하여 결과를 반환
     */
    @Override
    public List<Schedule> getAllSchedules(Long writerId, LocalDate updatedAt, Paging paging) {
        StringBuilder sqlBuilder =
                new StringBuilder("SELECT schedule_id, a.writer_id, name, email, task, password, a.created_at, " +
                        "a.updated_at FROM schedule a JOIN writer b ON a.writer_id = b.writer_id WHERE deleted_at " +
                        "IS NULL");

        // 동적으로 추가될 파라미터를 저장하는 리스트
        // 조건에 해당하는 값들이 null이 아닌 경우 이 리스트에 추가
        List<Object> params = new ArrayList<>();

        // writerId 조건이 있을 경우
        if (writerId != null) {
            sqlBuilder.append(" AND a.writer_id = ?");
            params.add(writerId);
        }

        // updatedAt 조건이 있을 경우
        if (updatedAt != null) {
            sqlBuilder.append(" AND DATE(a.updated_at) = ?");
            params.add(updatedAt);
        }

        // 수정일 기준 내림차순 정렬 & 페이징 처리
        params.add(paging.getPageSize());
        params.add(paging.getPageOffset());
        sqlBuilder.append(" ORDER BY a.updated_at DESC LIMIT ? OFFSET ?");

        // 최종 SQL 쿼리 생성
        String sql = sqlBuilder.toString();

        // 쿼리 실행
        return jdbcTemplate.query(sql, new ScheduleRowMapper(), params.toArray());
    }

    /** 일정 단건 조회
     *  scheduleId를 기준으로 단건 일정 조회
     *  소프트 딜리트 처리된 일정은 조회 되지 않음
     *  일치하는 일정이 없으면 null 반환
     */
    @Override
    public Schedule getScheduleByIdOrElseThrow(Long scheduleId) {
        sql = "SELECT schedule_id, a.writer_id, name, email, task, password, a.created_at,a.updated_at "
            + "FROM schedule a JOIN writer b ON a.writer_id = b.writer_id WHERE schedule_id = ? "
            + "AND a.deleted_at IS NULL ORDER BY a.updated_at DESC";
        List<Schedule> result = jdbcTemplate.query(sql, new ScheduleRowMapper(), scheduleId);
        return result.stream().findAny().orElse(null);
    }

    /** 비밀번호 조회
     *  scheduleId를 기준으로 해당 일정의 비밀번호 조회
     *  일치하는 일정이 없으면 null 반환
     */
    @Override
    public String getPasswordByIdOrElseThrow(Long scheduleId) {
        try {
            sql = "SELECT password FROM schedule WHERE schedule_id = ?";
            return jdbcTemplate.queryForObject(sql, new PasswordRowMapper(), scheduleId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 일정 수정
     * scheduleId에 해당하는 일정의 task 값 수정
     */
    @Override
    public void updateSchedule(Long scheduleId, String task) {
        sql = "UPDATE schedule SET task = ? WHERE schedule_id = ?";
        jdbcTemplate.update(sql, task, scheduleId);
    }

    /**
     * 일정 소프트 딜리트
     * schedule 테이블 deleted_at 필드에 현재 시간을 삽입해서 소프트 삭제 처리 (기본값 null)
     * 삭제된 행 수 반환
     */
    @Override
    public void deleteSchedule(Long scheduleId) {
        sql = "UPDATE schedule SET deleted_at = ? WHERE schedule_id = ?";
        jdbcTemplate.update(sql, LocalDateTime.now(), scheduleId);
    }

}
