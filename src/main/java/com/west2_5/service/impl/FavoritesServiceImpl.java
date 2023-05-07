package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.mapper.FavoritesMapper;
import com.west2_5.model.entity.Favorites;
import com.west2_5.service.FavoritesService;
import org.springframework.stereotype.Service;

/**
 * (Favorites)表服务实现类
 *
 * @author makejava
 * @since 2023-05-07 14:23:49
 */
@Service("favoritesService")
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {

}

