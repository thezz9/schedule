package com.thezz9.schedule.controller;

import com.thezz9.schedule.dto.Paging;
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
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(@RequestParam(required = false) Long authorId,
                                                                     @RequestParam(required = false) LocalDate updatedAt,
                                                                     @RequestParam(defaultValue = "0") int pageIndex,
                                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(scheduleService.getAllSchedules(authorId, updatedAt, new Paging(pageIndex, pageSize)), HttpStatus.OK);
    }

    /** 일정 단건 조회 */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long scheduleId) {
        return new ResponseEntity<>(scheduleService.getScheduleById(scheduleId), HttpStatus.OK);
    }

    /** 일정 수정 */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto.getTask(), dto.getPassword()), HttpStatus.OK);
    }

    /** 일정 삭제 */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
