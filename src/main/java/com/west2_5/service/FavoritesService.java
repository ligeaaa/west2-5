package com.west2_5.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.response.favorites.FavoritesDetails;

import java.util.List;


public interface FavoritesService extends IService<Favorites> {


    ResponseResult addFavorite(Long merchandiseId);

    List<FavoritesDetails> getFavorites(PageRequest pageRequest);

    ResponseResult deleteFavorites(Long merchandiseId);

    Boolean checkFavorites(Long merchandiseId);

}
