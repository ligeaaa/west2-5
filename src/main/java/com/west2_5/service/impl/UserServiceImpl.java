package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseCode;
import com.west2_5.common.ResponseResult;
import com.west2_5.controller.UserController;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;
import com.west2_5.mapper.UserMapper;
import com.west2_5.service.UserService;
import com.west2_5.utils.SendSmsUtil;
import com.west2_5.utils.SnowflakeUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.west2_5.common.ResponseCode.*;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    UserMapper userMapper;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public void sendCode(String phone) {

        // 判断手机号是否已被注册
        if (userMapper.findUserByPhone(phone) != null) {
            throw new BusinessException(ResponseCode.PHONE_HAS_EXITED);
        }

        // 生成5位随机数字验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));

        // 将验证码存入 Redis（有效期5分钟）
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

        // 调用短信发送接口发送验证码
        SendSmsUtil.sendCode(phone,code);
    }

    public User findUserByPhone(String phone) {
        User user = userMapper.findUserByPhone(phone);
        if(user == null){
            throw new BusinessException(ResponseCode.USER_UNKNOWN);
        }
        return user;
    }

    public void signIn(String phone, String password, String code) {

        if (userMapper.findUserByPhone(phone) != null) {
            throw new BusinessException(ResponseCode.PHONE_HAS_EXITED);
        }

        String redisCode = redisTemplate.opsForValue().get(phone);

        //判断验证码是否已过期
        if (redisCode == null) {
            throw new BusinessException(ResponseCode.VERIFY_CODE_EXPIRED);
        }
        //判断验证码是否正确
        if (!redisCode.equals(code)) {
            throw new BusinessException(ResponseCode.VERIFY_CODE_MISMATCH);
        }

        User user = new User();
        // salt 加密
        String salt = new SecureRandomNumberGenerator().nextBytes().toString(); // toHex()的话 Realm也要改动
        SimpleHash simpleHash = new SimpleHash("md5", password, salt,2);
        String encryptedPwd = simpleHash.toString();

        //logger.info("原始密码"+password);
        //logger.info("加密密码"+encryptedPwd);


        // 雪花算法生成 Id
        SnowflakeUtil idGenerator = new SnowflakeUtil(1, 1);
        long userId = idGenerator.nextId();

        user.setUserId(userId);
        user.setPhone(phone);
        user.setUserName(phone); //注册时先默认和手机号一样
        user.setPassword(encryptedPwd);
        user.setSalt(salt);

        userMapper.register(user);

    }

}

