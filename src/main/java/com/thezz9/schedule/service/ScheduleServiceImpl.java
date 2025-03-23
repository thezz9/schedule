package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.repository.ScheduleRepository;
import com.thezz9.schedule.repository.ScheduleRepositoryImpl;
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

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getAuthorId(), dto.getTask(), dto.getPassword());
        return new ScheduleResponseDto(scheduleRepository.createSchedule(schedule));
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(Long authorId, LocalDate updatedAt) {
        List<Schedule> allSchedules = scheduleRepository.getAllSchedules(authorId, updatedAt);
        return allSchedules.stream().map(ScheduleResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        return new ScheduleResponseDto(scheduleRepository.getScheduleById(id));
    }

    @Override
    public String getPasswordById(Long id) {
        return scheduleRepository.getPasswordById(id);
    }

    /**
     *  일정 수정 시 필수 값 검증 및 비밀번호 일치 여부 검증
     *  author, details, password는 필수 값으로 확인
     *  입력된 password가 실제 데이터베이스의 비밀번호와 일치하는지 검증
     * */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, String task, String password) {

        // 비밀번호 조회
        String dbPassword = getPasswordById(scheduleId);

        // 필수값 검증: password는 반드시 필요하고, author 또는 details 중 하나는 null이 아니어야 함
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력값입니다.");
        }

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "author 또는 details 중 하나는 반드시 존재해야 합니다.");
        }

        // 비밀번호 검증
        if (!password.equals(dbPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 수정
        int updatedRow = scheduleRepository.updateSchedule(scheduleId, task);

        // 수정된 데이터가 없을 경우
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + scheduleId + "인 일정이 존재하지 않습니다.");
        }

        // 수정된 일정 반환
        return new ScheduleResponseDto(scheduleRepository.getScheduleById(scheduleId));
    }

    /**
     *  일정 삭제 시 비밀번호 검증
     *  password는 필수 값으로 확인
     *  입력된 password가 실제 데이터베이스의 비밀번호와 일치하는지 검증
     */
    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, String password) {

        // 비밀번호 조회
        String dbPassword = getPasswordById(scheduleId);

        // 비밀번호가 입력되지 않았을 경우 예외 발생
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력값입니다.");
        }

        // 입력된 비밀번호와 실제 비밀번호가 일치하지 않을 경우 예외 발생
        if (!password.equals(dbPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 삭제
        int deletedRow = scheduleRepository.deleteSchedule(scheduleId);

        // 삭제된 일정이 없을 경우 예외 발생
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + scheduleId + "인 일정이 존재하지 않습니다.");
        }
    }

}
