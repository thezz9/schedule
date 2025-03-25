package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.repository.ScheduleRepository;
import com.thezz9.writer.entity.Writer;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     *  일정 생성
     *  ScheduleRequestDto를 받아 Schedule 객체를 생성하고, Repository를 통해 DB에 저장
     *  저장된 일정을 ScheduleResponseDto 형태로 반환
     */
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getWriterId(), dto.getTask(), dto.getPassword(), new Writer(dto.getWriterId()));
        return new ScheduleResponseDto(scheduleRepository.createSchedule(schedule));
    }

    /**
     *  일정 전체 조회
     *  조회된 일정들을 ScheduleResponseDto 형태로 반환
     *  페이지가 넘어갈 경우 [](빈배열) 반환
     */
    @Override
    public List<ScheduleResponseDto> getAllSchedules(Long writerId, LocalDate updatedAt, Pageable pageable) {
        List<Schedule> allSchedules = scheduleRepository.getAllSchedules(writerId, updatedAt, pageable);
        return allSchedules.stream().map(ScheduleResponseDto::new).collect(Collectors.toList());
    }

    /**
     *  일정 단건 조회
     *  scheduleId를 기준으로 단건 일정 조회
     *  일정이 없으면 404 상태 코드와 함께 예외 발생
     */
    @Override
    public ScheduleResponseDto getScheduleByIdOrElseThrow(Long scheduleId) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(scheduleId);
        return new ScheduleResponseDto(schedule);
    }

    /**
     *  일정 수정
     *  일정 수정 시 필수 값 검증 및 비밀번호 일치 여부 검증
     *  입력된 password와 DB 비밀번호가 일치하지 않으면 예외 발생
     * */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, String task, String password) {

        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(scheduleId);

        // 비밀번호 검증
        if (schedule.verifyPassword(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 수정
        scheduleRepository.updateSchedule(scheduleId, task);

        // 수정된 일정 반환
        return new ScheduleResponseDto(scheduleRepository.getScheduleByIdOrElseThrow(scheduleId));
    }

    /**
     *  일정 삭제
     *  password는 필수 값으로 확인
     *  입력된 password와 DB 비밀번호가 일치하지 않으면 예외 발생
     */
    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(scheduleId);

        // 입력된 비밀번호와 실제 비밀번호가 일치하지 않을 경우 예외 발생
        if (schedule.verifyPassword(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 삭제
        scheduleRepository.deleteSchedule(scheduleId);

    }

}
