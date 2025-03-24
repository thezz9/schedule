package com.thezz9.writer.service;

import com.thezz9.writer.dto.WriterRequestDto;
import com.thezz9.writer.dto.WriterResponseDto;

import java.util.List;

public interface WriterService {

    WriterResponseDto createWriter(WriterRequestDto dto);
    List<WriterResponseDto> getAllWriters();
    WriterResponseDto getWriterByIdOrElseThrow(Long writerId);

}
