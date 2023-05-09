package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into user(user_phone,user_pwd,user_name,user_salt) values (#{userPhone},#{userPwd},#{userName},#{userSalt})")
    void register(User user);

    @Select("select * from user where user_phone = #{userPhone}")
    User findUserByPhone(String userPhone);

}
