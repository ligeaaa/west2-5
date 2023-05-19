package com.west2_5.model.entity;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User{
    //主键@TableId
    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //0表示普通用户，1表示管理员，2表示超级管理员
    private String role;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private LocalDateTime createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private LocalDateTime updateTime;
    //加密用盐值
    private String userSalt;



}
