package com.thezz9.schedule.repository;

import com.thezz9.schedule.dto.Paging;
import com.thezz9.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    Schedule createSchedule(Schedule schedule);
    List<Schedule> getAllSchedules(Long writerId, LocalDate updatedAt, Paging paging);
    Schedule getScheduleByIdOrElseThrow(Long scheduleId);
    String getPasswordByIdOrElseThrow(Long scheduleId);
    void updateSchedule(Long scheduleId, String task);
    void deleteSchedule(Long scheduleId);

}
