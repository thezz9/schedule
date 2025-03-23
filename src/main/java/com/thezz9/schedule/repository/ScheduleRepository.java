package com.thezz9.schedule.repository;

import com.thezz9.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    Schedule createSchedule(Schedule schedule);
    List<Schedule> getAllSchedules(String author, LocalDate updatedAt);
    Schedule getScheduleById(Long id, boolean includePassword);
    String getPasswordById(Long id);
    int updateSchedule(Long id, String author, String details, String password);
    int deleteSchedule(Long id, String password);

}
