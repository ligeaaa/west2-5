package com.west2_5.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.constants.QueryPageParam;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.User;

import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.request.merchandise.SelectMerchandiseRequest;
import com.west2_5.model.request.merchandise.UpdateMerchandiseById;
import com.west2_5.model.request.user.AddUserRequest;
import com.west2_5.model.request.user.SelectUserRequest;
import com.west2_5.model.request.user.UpdateUserById;
import com.west2_5.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.west2_5.common.ErrorCode.NULL_ERROR;
import static com.west2_5.common.ErrorCode.PARAMS_ERROR;


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


    //region 增删改查

    /**
     * 增
     */
    @ApiOperation("增")
    @PostMapping("/add")
    public BaseResponse<ErrorCode> addUser(@RequestBody AddUserRequest addUserRequest) {
        if (addUserRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        //用户名
        String userName = addUserRequest.getUserName();
        //昵称
        String nickName = addUserRequest.getNickName();
        //密码
        String password = addUserRequest.getPassword();
        //邮箱
        String email = addUserRequest.getEmail();
        //手机号
        String phonenumber = addUserRequest.getPhonenumber();
        //用户性别（0男，1女，2未知）
        String sex = addUserRequest.getSex();
        //头像
        String avatar = addUserRequest.getAvatar();

        BaseResponse result = userService.addUser(userName, nickName, password, email, phonenumber, sex, avatar);

        return result;
    }

    /**
     * 删
     */
    @ApiOperation("删")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(Long id) {
        if (id <= 0) {
            return ResultUtils.error(PARAMS_ERROR);
        }
        return ResultUtils.success(userService.removeById(id));
    }

    /**
     * 改
     */
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

    /**
     * 查
     */
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




    //endregion





}

