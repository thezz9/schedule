package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> getAllSchedules(Long writerId, LocalDate updatedAt, Pageable pageable);
    ScheduleResponseDto getScheduleByIdOrElseThrow(Long scheduleId);
    ScheduleResponseDto updateSchedule(Long scheduleId, String task, String password);
    void deleteSchedule(Long scheduleId, String password);

}
