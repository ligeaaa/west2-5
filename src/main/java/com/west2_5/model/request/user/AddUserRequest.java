package com.west2_5.model.request.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddUserRequest {

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //手机验证码
    private String code;

}
