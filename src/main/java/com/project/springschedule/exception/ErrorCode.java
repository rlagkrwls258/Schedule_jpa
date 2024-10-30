package com.project.springschedule.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    PAGE_NOT_FOUND("ERR404",404, "존재하지않는 페이지입니다."),
    BAD_REQUEST("ERR400",404, "유효성 검사 실패"),
    INTERNAL_SERVER_ERROR("ERR500",500, "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요"),
    TASK_NOT_FOUND("ERR1404",404,"일정을 찾을 수 없습니다."),
    USER_FORBIDDEN("ERR1500",500,"권한이 없습니다."),
    COMMENT_NOT_FOUND("ERR2404",404,"댓글을 찾을 수 없습니다.");

    private final String code;
    private final int status;
    private final String message;

    ErrorCode(String code,int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }


}
