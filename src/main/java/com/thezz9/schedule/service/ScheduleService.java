package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.Paging;
import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> getAllSchedules(Long writerId, LocalDate updatedAt, Paging paging);
    ScheduleResponseDto getScheduleByIdOrElseThrow(Long scheduleId);
    String getPasswordByIdOrElseThrow(Long scheduleId);
    ScheduleResponseDto updateSchedule(Long scheduleId, String task, String password);
    void deleteSchedule(Long scheduleId, String password);

}
