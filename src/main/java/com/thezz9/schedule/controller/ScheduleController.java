package com.thezz9.schedule.controller;

import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /** 일정 생성 */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
    }

    /** 일정 전체 + 조건 조회 */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(@RequestParam(required = false) String author,
                                                                     @RequestParam(required = false) LocalDate updatedAt) {
        return new ResponseEntity<>(scheduleService.getAllSchedules(author, updatedAt), HttpStatus.OK);
    }

    /** 일정 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.getScheduleById(id, true), HttpStatus.OK);
    }

    /** 일정 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getAuthor(), dto.getDetails(), dto.getPassword()), HttpStatus.OK);
    }

    /** 일정 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(id, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
