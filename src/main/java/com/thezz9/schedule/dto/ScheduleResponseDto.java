package com.thezz9.schedule.dto;

import com.thezz9.schedule.entity.Schedule;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long scheduleId;
    private final String task;
    private final Long writerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     *  생성 이후 상태 변경 불가능
     *  Schedule 엔티티 객체를 기반으로 Dto 생성
     *  @param schedule Schedule 엔티티 객체
     */
    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.writerId = schedule.getWriter().getWriterId();
        this.name = schedule.getWriter().getName();
        this.email = schedule.getWriter().getEmail();
        this.task = schedule.getTask();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

}
