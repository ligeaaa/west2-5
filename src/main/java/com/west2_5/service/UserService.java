package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.model.entity.User;



/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
public interface UserService extends IService<User> {


    BaseResponse addUser(String userName, String nickName, String password, String email, String phonenumber, String sex, String avatar);

    BaseResponse updateUserById(Long id, String userName, String nickName, String password, String status, String email, String phonenumber, String sex, String avatar);

    void sendCode(String phone);

    User findUserByPhone(String phone);

    BaseResponse<ErrorCode> signIn(String userName, String nickName, String password, String email, String phonenumber, String sex, String avatar, String code);
}
