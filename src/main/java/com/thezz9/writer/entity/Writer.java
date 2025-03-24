package com.thezz9.writer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Writer {

    private Long writerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Writer(Long writerId) {
        this.writerId = writerId;
    }

    public Writer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Writer(Long writerId, String name, String email) {
        this.writerId = writerId;
        this.name = name;
        this.email = email;
    }

}
