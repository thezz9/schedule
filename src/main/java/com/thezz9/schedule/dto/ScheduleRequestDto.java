package com.thezz9.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private Long authorId;
    private String task;
    private String password;
    private LocalDateTime updatedAt;

}