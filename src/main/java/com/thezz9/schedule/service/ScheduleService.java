package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> getAllSchedules(String author, LocalDate updatedAt);
    ScheduleResponseDto getScheduleById(Long id, boolean includePassword);
    String getPasswordById(Long id);
    ScheduleResponseDto updateSchedule(Long id, String author, String details, String password);
    void deleteSchedule(Long id, String password);

}
