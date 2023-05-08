package com.west2_5.model.request.user;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author fzu
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号格式错误")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    private String password;


    @NotEmpty(message = "验证码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "验证码错误")
    private String verifyCode;
}
