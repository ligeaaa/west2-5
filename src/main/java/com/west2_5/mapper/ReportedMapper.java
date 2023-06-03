package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Reported;
import org.apache.ibatis.annotations.Mapper;


/**
 * 举报信息(Reported)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-03 22:10:06
 */
@Mapper
public interface ReportedMapper extends BaseMapper<Reported> {

}
