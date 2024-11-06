package com.team4.readit.global.exception;

import com.team4.readit.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleServerError(Exception e) {
        log.error("Server Error: ", e);
        return ApiResponse.failure("SERVER_ERROR", "서버 오류가 발생했습니다.");
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleBadRequest(BadRequestException e) {
        log.error("BadRequestException: {} - {}", e.getCode(), e.getMessage());
        return ApiResponse.failure(String.valueOf(e.getCode()), e.getMessage());
    }
}