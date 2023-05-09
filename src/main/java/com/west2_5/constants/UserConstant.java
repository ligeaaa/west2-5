package com.west2_5.constants;

import io.swagger.models.auth.In;

/**
 * 用户常量
 *
 * @author yupi
 */
public interface UserConstant {


    //  ------- 权限 --------

    /**
     * 普通用户权限
     */
    String NORMAL_ROLE = "0";

    /**
     * 管理员权限
     */
    String ADMIN_ROLE = "1";

    /**
     * 超级管理员权限
     */
    String SUPER_ADMIN_ROLE = "2";

    //  ------- 性别 --------

    /**
     * 男
     */
    String MALE = "0";

    /**
     * 女
     */
    String FEMALE = "1";

    /**
     * 未知
     */
    String UNKNOWN_GENDER = "2";

    //  ------- 状态 --------
    /**
     * 正常
     */
    String NORMAL_WORKING = "0";

    /**
     * 停用
     */
    String STOP_WORKING = "1";


}
