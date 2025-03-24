package com.thezz9.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private Long writerId;

    @NotNull(message = "할일을 입력해주세요.")
    @Size(max = 200, message = "200자 내로 입력해주세요.")
    private String task;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    private LocalDateTime updatedAt;

}