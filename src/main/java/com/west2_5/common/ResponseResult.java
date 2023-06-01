package com.west2_5.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {
    private final int code;
    private final String message;
    private final T data;

    public ResponseResult() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
        this.data = null;
    }


    public ResponseResult(T data) {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
        this.data = data;
    }


    public ResponseResult(int code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }


    // 默认成功（无数据返回）
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>();
    }

    // 成功返回数据
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data);
    }

    // 异常
    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(code, message,null);
    }

}

