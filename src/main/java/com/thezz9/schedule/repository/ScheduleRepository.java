package com.thezz9.schedule.repository;

import com.thezz9.schedule.dto.Paging;
import com.thezz9.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    Schedule createSchedule(Schedule schedule);
    List<Schedule> getAllSchedules(Long authorId, LocalDate updatedAt, Paging paging);
    Schedule getScheduleById(Long scheduleId);
    String getPasswordById(Long scheduleId);
    int updateSchedule(Long scheduleId, String task);
    int deleteSchedule(Long scheduleId);

}
