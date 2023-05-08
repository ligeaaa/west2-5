package com.west2_5.controller;


import com.west2_5.common.BaseResponse;
import com.west2_5.common.ResultUtils;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.user.PasswordLoginRequest;
import com.west2_5.model.request.user.UserRegisterRequest;
import com.west2_5.service.UserService;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@RestController
@RequestMapping("user")
public class UserController{
    @Resource
    private UserService userService;

//    @Resource
//    private RSA rsa;


    /**
     * 获取RSA公钥
     *
     * @return RSA公钥
     */
    //    @GetMapping("/rsa")
    //    public BaseResponse<String> getPublicKey() {
    //        return ResultUtils.success(rsa.getPublicKeyBase64());
    //    }


    //region 增删改查

//    /**
//     * 用户注册
//     *
//     * @param userRegisterRequest userRegisterRequest
//     * @return userid
//     */
//    @PostMapping("/register")
//    public BaseResponse<Long> userRegister(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
//
//        long result = userService.userRegister(userRegisterRequest);
//        return ResultUtils.success(result);
//    }
//
//    /**
//     * 通过手机号和密码登录
//     *
//     * @param loginRequest 用户密码登录结构体
//     * @return authentication
//     */
//    @PostMapping("/login/by/password")
//    public BaseResponse<User> loginByPassword(@RequestBody @Valid PasswordLoginRequest loginRequest) {
//        User user = userService.loginByPassword(loginRequest);
//
//        return ResultUtils.success(user);
//    }

    //endregion





}

