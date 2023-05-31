package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into user(user_id,phone,password,user_name,salt) values (#{userId},#{phone},#{password},#{userName},#{salt})")
    void register(User user);

    @Select("select * from user where phone = #{phone}")
    User findUserByPhone(String phone);

}
