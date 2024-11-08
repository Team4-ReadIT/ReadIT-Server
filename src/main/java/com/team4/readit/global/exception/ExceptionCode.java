package com.team4.readit.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    INVALID_PERIOD(1001, "지원되지 않는 기간입니다."),
    INVALID_ARTICLE(1002, "유효하지 않은 기사 번호입니다."),
    INVALID_JOB(1003, "올바르지 않은 직무입니다."),
    INVALID_USER(1004, "유효하지 않은 사용자입니다."),
    INTERNAL_SERVER_ERROR(9999, "서버에서 에러가 발생하였습니다."),

    // Auth 관련 예외 추가
    EMAIL_ALREADY_EXISTS(2000, "이미 존재하는 이메일입니다."),
    LOGIN_FAILED(2001, "이메일 또는 비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL_FORMAT(2002, "올바르지 않은 이메일 형식입니다.");
//    INVALID_PASSWORD_FORMAT(2003, "비밀번호는 8자 이상이어야 합니다.");

    private final int code;
    private final String message;
}
