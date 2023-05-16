package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.controller.UserController;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;
import com.west2_5.mapper.UserMapper;
import com.west2_5.service.UserService;
import com.west2_5.utils.SendSmsUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    UserMapper userMapper;

    @Resource
    RedisTemplate<String, String> redisTemplate;

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

    //region整合


    public void sendCode(String phonenumber) {

        // 判断手机号是否已被注册
        if (userMapper.findUserByPhone(phonenumber) != null) {
            throw new BusinessException(ErrorCode.PHONE_HAS_EXITED);
        }

        // 生成5位随机数字验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));

        // 将验证码存入 Redis（有效期5分钟）
        redisTemplate.opsForValue().set(phonenumber, code, 5, TimeUnit.MINUTES);

        // 调用短信发送接口发送验证码
        SendSmsUtil.sendCode(phonenumber,code);
    }




    public User findUserByPhone(String phonenumber) {
        User user = userMapper.findUserByPhone(phonenumber);
        if(user == null){
            throw new BusinessException(ErrorCode.USER_UNKNOWN);
        }
        return user;
    }

    @Override
    public BaseResponse<ErrorCode> signIn(String userName, String nickName, String password, String email, String phonenumber, String sex, String avatar, String code) {
        String redisCode = redisTemplate.opsForValue().get(phonenumber);
        //判断是否填写验证码
        if (code == null){
            return ResultUtils.error(NULL_ERROR,"验证码未填写");
        }
        //判断验证码是否已过期
        if (redisCode == null) {
            throw new BusinessException(ErrorCode.VERIFY_CODE_EXPIRED);
        }
        //判断验证码是否正确
        if (!redisCode.equals(code)) {
            throw new BusinessException(ErrorCode.VERIFY_CODE_MISMATCH);
        }

        User user = new User();


        // salt 加密
        String salt = new SecureRandomNumberGenerator().nextBytes().toString(); // toHex()的话 Realm也要改动
        SimpleHash simpleHash = new SimpleHash("md5", password, salt,2);
        String encryptedPwd = simpleHash.toString();

        //logger.info("原始密码"+password);
        //logger.info("加密密码"+encryptedPwd);
        //TODO 判断userid是否存在

        LocalDateTime localDateTime = LocalDateTime.now();
        user.setUserName(userName);
        user.setPassword(encryptedPwd);
        user.setPhonenumber(phonenumber);
        user.setUserSalt(salt);
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

        return ResultUtils.success(SUCCESS);
        // Todo: 解密匹配写在 UserRealm
    }


    //endregion


}

