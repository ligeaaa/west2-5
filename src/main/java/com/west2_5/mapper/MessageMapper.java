package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Message;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Message)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
