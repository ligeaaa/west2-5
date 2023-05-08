package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.user.PasswordLoginRequest;
import com.west2_5.model.request.user.UserRegisterRequest;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userRegisterRequest userRegisterRequest
     * @return id
     */
    Long userRegister(UserRegisterRequest userRegisterRequest);

    Boolean verifyPhone(String phone, String code, String key);

    /**
     * 通过密码登录
     *
     * @param loginRequest 用户密码登录结构体
     * @return User
     */
    User loginByPassword(PasswordLoginRequest loginRequest);
}
