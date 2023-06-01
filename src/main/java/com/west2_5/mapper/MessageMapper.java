package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Message;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Message)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-01 22:53:22
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
