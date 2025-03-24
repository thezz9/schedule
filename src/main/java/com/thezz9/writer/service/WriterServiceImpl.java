package com.thezz9.writer.service;

import com.thezz9.writer.dto.WriterRequestDto;
import com.thezz9.writer.dto.WriterResponseDto;
import com.thezz9.writer.entity.Writer;
import com.thezz9.writer.repository.WriterRepository;
import com.thezz9.writer.repository.WriterRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;
    private final WriterRepositoryImpl writerRepositoryImpl;

    public WriterServiceImpl(WriterRepository writerRepository, WriterRepositoryImpl writerRepositoryImpl) {
        this.writerRepository = writerRepository;
        this.writerRepositoryImpl = writerRepositoryImpl;
    }

    /**
     *  일정 생성
     *  ScheduleRequestDto를 받아 Schedule 객체를 생성하고, Repository를 통해 DB에 저장
     *  저장된 일정을 ScheduleResponseDto 형태로 반환
     */
    @Override
    public WriterResponseDto createWriter(WriterRequestDto dto) {
        Writer writer = new Writer(dto.getName(), dto.getEmail());
        return new WriterResponseDto(writerRepository.createWriter(writer));
    }

    /**
     *  일정 전체 조회
     *  조회된 일정들을 WriterResponseDto 형태로 반환
     *  페이지가 넘어갈 경우 [](빈배열) 반환
     */
    @Override
    public List<WriterResponseDto> getAllWriters() {
        List<Writer> allWriters = writerRepository.getAllWriters();
        return allWriters.stream().map(WriterResponseDto::new).collect(Collectors.toList());
    }

    /**
     *  일정 단건 조회
     *  writerId를 기준으로 단건 일정 조회
     *  일정이 없으면 404 상태 코드와 함께 예외 발생
     */
    @Override
    public WriterResponseDto getWriterByIdOrElseThrow(Long writerId) {
        Writer writer = writerRepository.getWriterByIdOrElseThrow(writerId);
        if (writer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 " + writerId + "인 회원이 존재하지 않습니다.");
        }
        return new WriterResponseDto(writer);
    }

}
