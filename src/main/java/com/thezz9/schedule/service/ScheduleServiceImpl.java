package com.thezz9.schedule.service;

import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.entity.Schedule;
import com.thezz9.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTitle(), dto.getAuthor(), dto.getDetails(), dto.getPassword());
        return scheduleRepository.createSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.getAllSchedules();
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id, boolean includePassword) {
        return scheduleRepository.getScheduleById(id, includePassword);
    }

    @Override
    public List<ScheduleResponseDto> getSchedulesByCondition(String author, LocalDate updatedAt) {
        return scheduleRepository.getSchedulesByCondition(author, updatedAt);
    }

    @Override
    public ScheduleResponseDto getPasswordById(Long id) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String author, String details, String password) {
        return null;
    }

    @Override
    public void deleteSchedule(Long id, String password) {

    }

}