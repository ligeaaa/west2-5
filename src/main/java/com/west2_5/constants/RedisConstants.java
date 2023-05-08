package com.west2_5.constants;

/**
 * <p>
 * 和Redis相关的常量，包括但不限于Key的前缀，过期时间
 * <p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-03-18
 **/
public interface RedisConstants {

    /**
     * 手机验证码注册前缀
     */
    String REGISTER_CODE_KEY = "sms:register:code:";

    /**
     * 手机验证码更新前缀
     */
    String UPDATE_CODE_KEY = "sms:update:code:";


    /**
     * 手机验证码登录前缀
     */
    String LOGIN_CODE_KEY = "sms:login:code:";

    /**
     * 手机验证码过期时间
     */
    Long PHONE_CODE_TTL = 2L;


    /**
     * 用户基本信息
     */
    String USER_INFO = "user:info:";

    /**
     * 用户基本信息过期时间
     */
    Long USER_INFO_TTL = 30L;

    /**
     * 用户角色前缀
     */
    String USER_ROLE = "user:role:";

    /**
     * 用户角色信息过期时间
     */
    Long USER_ROLE_TTL = 30L;


    /**
     * 任务提交前缀
     */
    String TASK_SUBMIT = "task:submit";


}
