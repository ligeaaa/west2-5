package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.MerchandiseTag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章标签关联表(MerchandiseTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Mapper
public interface MerchandiseTagMapper extends BaseMapper<MerchandiseTag> {

}
