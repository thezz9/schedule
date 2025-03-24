package com.thezz9.writer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WriterRequestDto {

    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    @NotNull
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;


}