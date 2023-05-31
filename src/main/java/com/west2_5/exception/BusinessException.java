package com.west2_5.exception;

import com.west2_5.common.ResponseCode;

public class BusinessException extends RuntimeException {

    private int code;
    private String message;


    public BusinessException(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
