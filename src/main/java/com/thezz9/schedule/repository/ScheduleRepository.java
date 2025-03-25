package com.thezz9.schedule.repository;

import com.thezz9.schedule.entity.Schedule;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    Schedule createSchedule(Schedule schedule);
    List<Schedule> getAllSchedules(Long writerId, LocalDate updatedAt, Pageable pageable);
    Schedule getScheduleByIdOrElseThrow(Long scheduleId);
    void updateSchedule(Long scheduleId, String task);
    void deleteSchedule(Long scheduleId);

}
