package com.west2_5.model.request.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 *  TODO
 * <p>
 *
 * @author Zaki
 * @version TODO
 * @since 2023-04-09
 **/
@Data
public class UpdateUserPhoneRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 旧手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号格式错误")
    private String oldPhone;

    /**
     * 旧验证码
     */
    @NotEmpty(message = "验证码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "验证码错误")
    private String oldCode;

    /**
     * 新手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号格式错误")
    private String newPhone;

    /**
     * 新验证码
     */
    @NotEmpty(message = "验证码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "验证码错误")
    private String newCode;
}
