package com.west2_5.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.user.AddUserRequest;
import com.west2_5.model.response.user.UserBasicInfo;
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

import static com.west2_5.common.ResponseCode.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    //发送短信
    @GetMapping("/sms")
    public ResponseResult sendCode(@RequestParam String phone) {
        userService.sendCode(phone);
        return ResponseResult.success();
    }

    //检验验证码 + 注册
    @PostMapping("/signin")
    public ResponseResult validRegister(@RequestBody AddUserRequest addUserRequest) {

        String phone = addUserRequest.getPhone();
        String password = addUserRequest.getPassword();
        String code = addUserRequest.getCode();

        userService.signIn(phone, password, code);
        return ResponseResult.success();
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {

        String phone = user.getPhone();
        String password = user.getPassword();

        UsernamePasswordToken auth = new UsernamePasswordToken(phone, password); //用于和原本UserRealm生成的token对比
        Subject subject = SecurityUtils.getSubject();
        Serializable tokenId = "";

        try {
            subject.login(auth);
            User authUser = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = authUser.getUserId();
            tokenId = subject.getSession().getId();
        } catch (AuthenticationException e) {
            if (e instanceof IncorrectCredentialsException) {
               throw new BusinessException(INCORRECT_PWD);
            }
        }

        return ResponseResult.success(tokenId);
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
    public ResponseResult<JSONObject> getBasicInfo() {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户（不用重新调数据库）

        String name = user.getUserName();
        String avatar = user.getAvatar();

        UserBasicInfo basicInfo = new UserBasicInfo();
        basicInfo.setUserName(name);
        basicInfo.setAvatar(avatar);

        JSONObject info=(JSONObject)JSON.toJSON(basicInfo);

        return ResponseResult.success(info);
    }

}

