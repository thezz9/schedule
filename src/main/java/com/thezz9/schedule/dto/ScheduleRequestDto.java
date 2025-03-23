package com.thezz9.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private String title;
    private String author;
    private String details;
    private String password;
    private LocalDateTime updatedAt;

}