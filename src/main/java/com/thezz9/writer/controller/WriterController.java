package com.thezz9.writer.controller;

import com.thezz9.writer.dto.WriterRequestDto;
import com.thezz9.writer.dto.WriterResponseDto;
import com.thezz9.writer.service.WriterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/writers")
@CrossOrigin(origins = "*")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    /** 회원 생성 API
     *  이름과 이메일을 입력받아 새 회원 생성
     *  생성된 회원 정보 반환
     */
    @PostMapping
    public ResponseEntity<WriterResponseDto> createWriter(@RequestBody @Valid WriterRequestDto dto) {
        return new ResponseEntity<>(writerService.createWriter(dto), HttpStatus.CREATED);
    }

    /** 회원 전체 조회 API */
    @GetMapping
    public ResponseEntity<List<WriterResponseDto>> getAllWriters() {
        return new ResponseEntity<>(writerService.getAllWriters(), HttpStatus.OK);
    }

    /** 회원 단건 조회 API
     *  특정 회원 ID를 받아 해당 회원 정보를 반환
     *  회원이 없을 경우 404 예외 발생
     */
    @GetMapping("/{writerId}")
    public ResponseEntity<WriterResponseDto> getWriterByIdOrElseThrow(@PathVariable Long writerId) {
        return new ResponseEntity<>(writerService.getWriterByIdOrElseThrow(writerId), HttpStatus.OK);
    }

}
