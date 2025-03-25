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

    public Schedule(Long scheduleId, String task, String password, LocalDateTime createdAt,
                    LocalDateTime updatedAt, Writer writer) {
        this.scheduleId = scheduleId;
        this.task = task;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.writer = writer;
    }

    /**
     *  검증을 위해 scheduleId 값으로 단건 조회를 할 경우 생성자가 초기화
     *  그 값을 이용해 입력된 패스워드와 비교하는 메서드
     */
    public boolean verifyPassword(String password) {
        return !this.password.equals(password);
    }

}
