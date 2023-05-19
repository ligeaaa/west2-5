package com.west2_5.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.constants.QueryPageParam;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;

import com.west2_5.model.request.user.AddUserRequest;
import com.west2_5.model.request.user.SelectUserRequest;
import com.west2_5.model.request.user.UpdateUserById;
import com.west2_5.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.west2_5.common.ErrorCode.*;

@RestController
@RequestMapping("user")
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

        String Phonenumber = user.getPhonenumber();
        String password = user.getPassword();

        UsernamePasswordToken auth = new UsernamePasswordToken(Phonenumber, password); //用于和原本UserRealm生成的token对比
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(auth);
            User authUser = (User) SecurityUtils.getSubject().getPrincipal();
            Long userId = authUser.getId();
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
        String phonenumber = user.getPhonenumber();
        String name = user.getUserName();
        String avatar = user.getAvatar();

        JSONObject userInfo = new JSONObject();
        userInfo.put("phone", phonenumber);
        userInfo.put("name", name);
        userInfo.put("avatar", avatar);

        return ResultUtils.success(userInfo);
    }


    @ApiOperation("删")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(Long id) {
        if (id <= 0) {
            return ResultUtils.error(PARAMS_ERROR);
        }
        return ResultUtils.success(userService.removeById(id));
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public BaseResponse updateUserById(@RequestBody UpdateUserById updateUserById, HttpServletRequest request) {
        if (updateUserById == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        Long id = updateUserById.getId();
        //用户名
        String userName = updateUserById.getUserName();
        //昵称
        String nickName = updateUserById.getNickName();
        //密码
        String password = updateUserById.getPassword();
        //状态
        String status = updateUserById.getStatus();
        //邮箱
        String email = updateUserById.getEmail();
        //手机号
        String phonenumber = updateUserById.getPhonenumber();
        //用户性别（0男，1女，2未知）
        String sex = updateUserById.getSex();
        //头像
        String avatar = updateUserById.getAvatar();

        return userService.updateUserById(id, userName, nickName, password, status, email, phonenumber, sex, avatar);
    }


    @ApiOperation("查询")
    @PostMapping("/select")
    public BaseResponse<List<User>> selectUser(@RequestBody SelectUserRequest selectUserRequest, HttpServletRequest request) {
        //判断selectUserRequest是否为空
        if (selectUserRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        Long id = selectUserRequest.getId();
        //用户名
        String userName = selectUserRequest.getUserName();
        //昵称
        String nickName = selectUserRequest.getNickName();
        //密码
        String password = selectUserRequest.getPassword();
        //状态
        String status = selectUserRequest.getStatus();
        //邮箱
        String email = selectUserRequest.getEmail();
        //手机号
        String phonenumber = selectUserRequest.getPhonenumber();
        //用户性别（0男，1女，2未知）
        String sex = selectUserRequest.getSex();


        Long current = selectUserRequest.getCurrent();
        Long pageSize = selectUserRequest.getPageSize();
        QueryPageParam queryPageParam = new QueryPageParam();

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();

        //若属性不为空=要查询该属性
        if (id != null) {
            lambdaQueryWrapper.like(User::getId, id);
        }
        if (userName != null) {
            lambdaQueryWrapper.like(User::getUserName, userName);
        }
        if (nickName != null) {
            lambdaQueryWrapper.like(User::getNickName, nickName);
        }
        if (password != null) {
            lambdaQueryWrapper.like(User::getPassword, password);
        }
        if (status != null) {
            lambdaQueryWrapper.like(User::getStatus, status);
        }
        if (email != null) {
            lambdaQueryWrapper.like(User::getEmail, email);
        }
        if (phonenumber != null) {
            lambdaQueryWrapper.like(User::getPhonenumber, phonenumber);
        }
        if (sex != null) {
            lambdaQueryWrapper.like(User::getSex, sex);
        }

        //current默认为20，pageSize默认为1

        queryPageParam.setPageNum(current);

        queryPageParam.setPageSize(pageSize);

        Page<User> page = new Page();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        IPage<User> result = userService.page(page, lambdaQueryWrapper);

        return ResultUtils.success(result.getRecords());
    }

}

