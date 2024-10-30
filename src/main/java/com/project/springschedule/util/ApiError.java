package com.project.springschedule.util;

import lombok.Getter;

@Getter
public class ApiError {
    private final String msg;
    private final String status;

    public ApiError(String msg, String status) {
        this.msg = msg;
        this.status = status;
    }
}
