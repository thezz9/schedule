package com.thezz9.schedule.entity;

import com.thezz9.writer.entity.Writer;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Schedule {

    private Long scheduleId;
    private String task;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Writer writer; // 작성자 정보

    public Schedule(Long scheduleId, String task, String password, Writer writer) {
        this.scheduleId = scheduleId;
        this.task = task;
        this.password = password;
        this.writer = writer;
    }

    public Schedule(Long scheduleId, String task, String password, Long writerId, String name,
                    String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.task = task;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.writer = new Writer(writerId, name, email);
    }

}
