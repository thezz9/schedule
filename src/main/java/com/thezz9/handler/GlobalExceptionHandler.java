package com.thezz9.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  전역 예외 처리
     *  ResponseStatusException이 발생하면 JSON 응답을 반환함
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", ex.getStatusCode().value()); // HTTP 상태 코드
        error.put("error", HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase()); // 상태 코드 설명
        error.put("message", ex.getReason()); // 예외 발생 시 전달된 메시지
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    /**
     *  유효성 검증 예외 처리
     *  MethodArgumentNotValidException이 발생하면 응답을 반환함
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("실패: " + ex.getBindingResult());
    }

    // MissingServletRequestParameterException 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParamExceptions(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("필수 값 " + ex.getParameterName() + "을(를) 입력하세요.");
    }

}
