package com.west2_5.common;

public enum ResponseCode {

    SUCCESS(0, "OK"),

    //数据异常
    PARAMS_ERROR(30000, "请求参数错误"),
    NULL_ERROR(30001, "参数为空"),
    IMAGE_ERROR(30002,"图片上传异常"),

    //业务相关
    PHONE_HAS_EXITED(40001,"手机号已被注册"),
    VERIFY_CODE_EXPIRED(40002,"验证码已过期"),
    VERIFY_CODE_MISMATCH(40003,"验证码错误"),
    USER_UNKNOWN(40004,"用户不存在"),
    INCORRECT_PWD(40005,"密码错误"),
    USER_NOT_LOGIN(40006,"用户未登录"),

    //系统异常
    SYSTEM_ERROR(50000, "系统内部异常");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
