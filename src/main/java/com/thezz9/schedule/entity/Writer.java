package com.thezz9.schedule.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Writer {

    private Long writerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Writer(Long writerId) {
        this.writerId = writerId;
    }

    public Writer(Long writerId, String name, String email) {
        this.writerId = writerId;
        this.name = name;
        this.email = email;
    }

}
