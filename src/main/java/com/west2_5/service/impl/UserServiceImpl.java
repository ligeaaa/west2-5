package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ResultUtils;
import com.west2_5.model.entity.User;
import com.west2_5.model.entity.User;
import com.west2_5.mapper.UserMapper;
import com.west2_5.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.west2_5.common.ErrorCode.*;
import static com.west2_5.constants.UserConstant.*;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public BaseResponse addUser(String userName, String nickName, String password, String email, String phonenumber, String sex, String avatar) {
        if (userName == null || password == null){
            return ResultUtils.error(PARAMS_ERROR);
        }
        //实现add功能
        User user = new User();

        //TODO 判断userid是否存在

        LocalDateTime localDateTime = LocalDateTime.now();
        user.setUserName(userName);
        user.setPassword(password);
        user.setStatus(NORMAL_WORKING);
        user.setRole(NORMAL_ROLE);
        user.setCreateTime(localDateTime);
        user.setUpdateTime(localDateTime);

        //region 各属性null的判断以及对应的处理
        if (nickName == null){
            user.setNickName(userName);
        }else{
            user.setNickName(nickName);
        }

        if (email == null){
            user.setEmail("null");
        }else{
            user.setEmail(email);
        }

        if (phonenumber == null){
            user.setPhonenumber("null");
        }else{
            user.setPhonenumber(phonenumber);
        }

        if (sex == null){
            user.setSex(UNKNOWN_GENDER);
        }else{
            user.setSex(sex);
        }

        if (avatar == null){
            user.setAvatar("默认路径");
        }else{
            user.setAvatar(avatar);
        }
        //endregion

        boolean saveResult = this.save(user);
        if (saveResult){
            return ResultUtils.success(SUCCESS);
        }else{
            return ResultUtils.error(SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse updateUserById(Long id, String userName, String nickName, String password, String status, String email, String phonenumber, String sex, String avatar) {
        //判断为空,判断id是否合法,判断id是否存在
        if (id == null || id <= 0 || this.getById(id) == null){
            return ResultUtils.error(PARAMS_ERROR);
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        if (id != null){
            updateWrapper.lambda().set(User::getId,id);
        }
        if (userName != null){
            updateWrapper.lambda().set(User::getUserName,userName);
        }
        if (nickName != null){
            updateWrapper.lambda().set(User::getNickName,nickName);
        }
        if (password != null){
            updateWrapper.lambda().set(User::getPassword,password);
        }
        if (status != null){
            updateWrapper.lambda().set(User::getStatus,status);
        }
        if (email != null){
            updateWrapper.lambda().set(User::getEmail,email);
        }
        if (phonenumber != null){
            updateWrapper.lambda().set(User::getPhonenumber,phonenumber);
        }
        if (sex != null){
            updateWrapper.lambda().set(User::getSex,sex);
        }
        if (avatar != null){
            updateWrapper.lambda().set(User::getAvatar,avatar);
        }

        updateWrapper.lambda().eq(User::getId,id);//判断依据
        userMapper.update(null,updateWrapper);
        return ResultUtils.success(SUCCESS);
    }
}

