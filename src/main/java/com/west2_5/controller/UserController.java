package com.west2_5.controller;


import com.alibaba.fastjson2.JSONObject;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.user.AddUserRequest;
import com.west2_5.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

import static com.west2_5.common.ErrorCode.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    //region 整合内容

    //发送短信
    @GetMapping("/sms")
    public BaseResponse<ErrorCode> sendCode(@RequestParam String phonenumber) {
        userService.sendCode(phonenumber);
        return ResultUtils.success(SUCCESS);
    }

    //检验验证码 + 注册
    @PostMapping("/signin")
    public BaseResponse<ErrorCode> validRegister(@RequestBody AddUserRequest addUserRequest) {
        if (addUserRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        //手机号
        String phone = addUserRequest.getPhone();

        //密码
        String password = addUserRequest.getPassword();

        //验证码
        String code = addUserRequest.getCode();

        userService.signIn(phone, password, code);
        return ResultUtils.success(SUCCESS);
    }

    @PostMapping("/login")
    public BaseResponse<Serializable> login(@RequestBody User user) {

        String phone = user.getPhone();
        String password = user.getPassword();

        UsernamePasswordToken auth = new UsernamePasswordToken(phone, password); //用于和原本UserRealm生成的token对比
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(auth);
            User authUser = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = authUser.getUserId();
            Serializable tokenId = subject.getSession().getId();
            JSONObject authInfo = new JSONObject();
            authInfo.put("userId",userId);
            authInfo.put("token",tokenId);
            return ResultUtils.success(authInfo);
        } catch (AuthenticationException e) {
            if (e instanceof IncorrectCredentialsException) {
               return ResultUtils.error(INCORRECT_PWD);
            }
        }
        // FIXME: 拦截后仍返回success
        return ResultUtils.error(USER_UNKNOWN);
    }

    @PostMapping("/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
    }

    // 获取用户基本信息
    @GetMapping("/info")
    public BaseResponse<JSONObject> getBasicInfo() {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户（不用重新调数据库）
        String phone = user.getPhone();
        String name = user.getUserName();
        String avatar = user.getAvatar();

        JSONObject userInfo = new JSONObject();
        userInfo.put("phone", phone);
        userInfo.put("name", name);
        userInfo.put("avatar", avatar);

        return ResultUtils.success(userInfo);
    }

}

