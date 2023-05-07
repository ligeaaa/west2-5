package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Favorites;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Favorites)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-07 14:23:40
 */
@Mapper
public interface FavoritesMapper extends BaseMapper<Favorites> {

}
