package com.project.springschedule.util;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;


@Getter
@JsonPropertyOrder({"success", "data", "apiError"})
public class ApiResult<T> {

    private final ApiError apiError;
    private final boolean success;
    private final T data;

    public ApiResult(boolean success, T data, ApiError apiError) {
        this.apiError = apiError;
        this.success = success;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static <T> ApiResult<T> error(String status, String msg) {
        return new ApiResult<>(false, null, new ApiError(msg, status));
    }

    public static <T> ApiResult<T> error(String status, String msg, T errorMap) {
        return new ApiResult<>(false, errorMap, new ApiError(msg, status));
    }

}
