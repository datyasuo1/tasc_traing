package com.example.smart_shop.model.response;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private int code;

    private String message;

    private T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(T data) {
        this.code = 1;
        this.message = "SUCCESS";
        this.data = data;
    }

    public BaseResponse(ERROR error) {
        this.code = error.code;
        this.message = error.message;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
