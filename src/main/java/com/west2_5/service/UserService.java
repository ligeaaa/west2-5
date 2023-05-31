package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.model.entity.User;

public interface UserService extends IService<User> {

    void sendCode(String phone);

    User findUserByPhone(String phone);

    BaseResponse<ErrorCode> signIn(String phone,String password,String code);

}
