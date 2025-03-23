package com.thezz9.schedule.repository;

import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto createSchedule(Schedule schedule);
    List<ScheduleResponseDto> getAllSchedules();
    ScheduleResponseDto getScheduleById(Long id, boolean includePassword);
    List<ScheduleResponseDto> getSchedulesByCondition(String author, LocalDate updatedAt);
    ScheduleResponseDto getPasswordById(Long id);
    ScheduleResponseDto updateSchedule(Long id, String author, String details, String password);
    void deleteSchedule(Long id, String password);

}
