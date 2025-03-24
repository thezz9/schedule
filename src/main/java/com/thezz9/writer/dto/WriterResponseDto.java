package com.thezz9.writer.dto;

import com.thezz9.writer.entity.Writer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WriterResponseDto {

    private final Long writerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     *  생성 이후 상태 변경 불가능
     *  Writer 엔티티 객체를 기반으로 Dto 생성
     *  @param writer writer 엔티티 객체
     */
    public WriterResponseDto(Writer writer) {
        this.writerId = writer.getWriterId();
        this.name = writer.getName();
        this.email = writer.getEmail();
        this.createdAt = writer.getCreatedAt();
        this.updatedAt = writer.getUpdatedAt();
    }

}
