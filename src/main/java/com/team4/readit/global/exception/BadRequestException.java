package com.team4.readit.global.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int code;
    private final String message;

    public BadRequestException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public BadRequestException(final ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getMessage(), cause);
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
