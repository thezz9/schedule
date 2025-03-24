package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.Paging;
import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.repository.ScheduleRepository;
import com.thezz9.schedule.repository.ScheduleRepositoryImpl;
import com.thezz9.writer.entity.Writer;
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
    private final ScheduleRepositoryImpl scheduleRepositoryImpl;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleRepositoryImpl scheduleRepositoryImpl) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleRepositoryImpl = scheduleRepositoryImpl;
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
    public List<ScheduleResponseDto> getAllSchedules(Long writerId, LocalDate updatedAt, Paging paging) {
        List<Schedule> allSchedules = scheduleRepository.getAllSchedules(writerId, updatedAt, paging);
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
        if (schedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + scheduleId + "인 일정이 존재하지 않습니다.");
        }
        return new ScheduleResponseDto(schedule);
    }

    /**
     *  비밀번호 조회
     *  scheduleId를 기준으로 비밀번호를 조회
     *  비밀번호가 없으면 404 상태 코드와 함께 예외 발생
     */
    @Override
    public String getPasswordByIdOrElseThrow(Long scheduleId) {
        String password = scheduleRepository.getPasswordByIdOrElseThrow(scheduleId);
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정의 비밀번호를 찾을 수 없습니다.");
        }
        return password;
    }

    /**
     *  일정 수정
     *  일정 수정 시 필수 값 검증 및 비밀번호 일치 여부 검증
     *  입력된 password와 DB 비밀번호가 일치하지 않으면 예외 발생
     * */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, String task, String password) {

        // 해당 일정이 존재하는지 확인
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(scheduleId);
        if (schedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + scheduleId + "인 일정이 존재하지 않습니다.");
        }

        // 비밀번호 조회
        String dbPassword = getPasswordByIdOrElseThrow(scheduleId);

        // 필수값 검증
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력값입니다.");
        }

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "task는 필수 입력값입니다.");
        }

        // 비밀번호 검증
        if (!password.equals(dbPassword)) {
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

        // 해당 일정이 존재하는지 확인
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(scheduleId);
        if (schedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + scheduleId + "인 일정이 존재하지 않습니다.");
        }

        // 비밀번호 조회
        String dbPassword = getPasswordByIdOrElseThrow(scheduleId);

        // 비밀번호가 입력되지 않았을 경우 예외 발생
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력값입니다.");
        }

        // 입력된 비밀번호와 실제 비밀번호가 일치하지 않을 경우 예외 발생
        if (!password.equals(dbPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 삭제
        scheduleRepository.deleteSchedule(scheduleId);

    }

}
