package com.thezz9.schedule.dto;

import com.thezz9.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String author;
    private final String details;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     *  생성 이후 상태 변경 불가능
     *  Schedule 엔티티 객체를 기반으로 Dto 생성
     *  @param schedule Schedule 엔티티 객체
     * */
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.author = schedule.getAuthor();
        this.details = schedule.getDetails();
        this.password = schedule.getPassword();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

}
