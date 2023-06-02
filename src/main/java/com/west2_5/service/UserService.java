package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.ResponseCode;
import com.west2_5.model.entity.User;

public interface UserService extends IService<User> {

    void sendCode(String phone);

    User findUserByPhone(String phone);

    void signIn(String phone, String password, String code);

    User getByUserId(Long userId);

    void checkAdmin();
}
