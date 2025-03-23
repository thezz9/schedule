package com.thezz9.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;
    private String task;
    private String password;
    private Long authorId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(Long authorId, String task, String password) {
        this.authorId = authorId;
        this.task = task;
        this.password = password;
    }

}
