package com.team4.readit.global.exception;

public class InvalidInputException extends BadRequestException {
    public InvalidInputException(ExceptionCode code) {
        super(code);
    }
}