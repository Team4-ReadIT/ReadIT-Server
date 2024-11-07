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
    INVALID_USER(1003, "유효하지 않은 사용자입니다."),
    INTERNAL_SERVER_ERROR(9999, "서버에서 에러가 발생하였습니다.");

    private final int code;
    private final String message;
}
