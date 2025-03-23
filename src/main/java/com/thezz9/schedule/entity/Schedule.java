package com.thezz9.schedule.entity;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Schedule {

    private Long id;
    private String title;
    private String author;
    private String details;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String title, String author, String details, String password) {
        this.title = title;
        this.author = author;
        this.details = details;
        this.password = password;
    }

    public Schedule(Long id, String title, String author, String details, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.details = details;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
