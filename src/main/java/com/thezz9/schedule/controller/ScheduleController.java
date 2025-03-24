package com.thezz9.schedule.controller;

import com.thezz9.schedule.dto.Paging;
import com.thezz9.schedule.dto.ScheduleRequestDto;
import com.thezz9.schedule.dto.ScheduleResponseDto;
import com.thezz9.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice // 예외 처리를 전역적으로 담당
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 전역 예외 처리
     * ResponseStatusException이 발생하면 JSON 응답을 반환함
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", ex.getStatusCode().value()); // HTTP 상태 코드
        error.put("error", HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase()); // 상태 코드 설명
        error.put("message", ex.getReason()); // 예외 발생 시 전달된 메시지
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    /** 일정 생성 API
     *  일정과 비밀번호를 입력받아 새 일정 생성
     *  생성된 일정 정보 반환
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
    }

    /** 일정 전체 조회 API
     *  writerId: 특정 작성자의 일정만 조회 (선택)
     *  updatedAt: 특정 날짜 이후로 수정된 일정만 조회 (선택)
     *  pageIndex, pageSize: 페이징 처리 (기본값: 0페이지, 10개)
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(@RequestParam(required = false) Long writerId,
                                                                     @RequestParam(required = false) LocalDate updatedAt,
                                                                     @RequestParam(defaultValue = "0") int pageIndex,
                                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(scheduleService.getAllSchedules(writerId, updatedAt, new Paging(pageIndex, pageSize)), HttpStatus.OK);
    }

    /** 일정 단건 조회 API
     *  특정 일정 ID를 받아 해당 일정 정보를 반환
     *  일정이 없을 경우 404 예외 발생
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long scheduleId) {
        return new ResponseEntity<>(scheduleService.getScheduleByIdOrElseThrow(scheduleId), HttpStatus.OK);
    }

    /** 일정 수정 API
     *  일정 ID와 수정할 내용을 입력받아 일정 업데이트
     *  비밀번호가 일치해야 수정 가능
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto.getTask(), dto.getPassword()), HttpStatus.OK);
    }

    /** 일정 삭제 API (소프트 딜리트 적용)
     *  일정 ID를 받아 해당 일정 삭제
     *  비밀번호가 일치해야 삭제 가능
     *  '삭제 여부' 컬럼을 업데이트하여 논리적으로 삭제 처리
     *  향후 복구 가능하도록 데이터는 유지
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
