package com.west2_5.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;
import com.west2_5.mapper.UserMapper;
import com.west2_5.model.request.user.UserRegisterRequest;
import com.west2_5.service.UserService;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.west2_5.common.ErrorCode.OPERATION_ERROR;
import static com.west2_5.common.ErrorCode.PARAMS_ERROR;
import static com.west2_5.constants.RedisConstants.REGISTER_CODE_KEY;
import static com.west2_5.constants.RoleConstants.DEFAULT_NICK_NAME_PREFIX;
import static com.west2_5.constants.RoleConstants.ROLE_USER;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RSA rsa;

    @Resource
    private Snowflake snowflake;


    /**
     * 实现验证 验证码和手机号是否匹配
     *
     * @param phone phone
     * @param code  验证码
     * @param key   redis存储对应验证码的前缀
     * @return 是否匹配
     */
    @Override
    public Boolean verifyPhone(String phone, String code, String key) {

        String cacheCode = stringRedisTemplate.opsForValue().get(key + phone);

        return StrUtil.equals(code, cacheCode);
    }

    @Override
    public Long userRegister(UserRegisterRequest userRegisterRequest) {
        String password = userRegisterRequest.getPassword();
        String phone = userRegisterRequest.getPhone();
        String code = userRegisterRequest.getVerifyCode();

//        try {
//            password = rsa.decryptStr(password, KeyType.PrivateKey);
//        } catch (Exception e) {
//            throw new BusinessException(PARAMS_ERROR, "密码未加密");
//        }


        // 验证 手机号和验证码是否匹配
        if (!verifyPhone(phone, code, REGISTER_CODE_KEY)) {
            throw new BusinessException(PARAMS_ERROR, "验证码错误");
        }


        // 手机号不能重复
        User one = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));

        if (BeanUtil.isNotEmpty(one)) {
            throw new BusinessException(OPERATION_ERROR, "该手机号已经注册");
        }

        // 生成用户id
        Long id = snowflake.nextId();

        // 加密
        String newPassword = SmUtil.sm3(id + password);

        // 插入数据
        User user = new User();
        user.setId(id);
        user.setPhonenumber(phone);
        user.setPassword(newPassword);
        user.setRole(ROLE_USER);
        user.setCreateTime(LocalDateTime.now());
        user.setNickName(DEFAULT_NICK_NAME_PREFIX + RandomUtil.randomString(10));

        boolean b = save(user);
        if (!b) {
            throw new BusinessException(OPERATION_ERROR, "注册失败");
        }

        return id;
    }
}

