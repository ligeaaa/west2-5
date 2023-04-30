package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Menu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-30 02:24:05
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
