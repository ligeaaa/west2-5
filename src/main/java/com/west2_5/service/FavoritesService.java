package com.west2_5.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.request.favorites.AddFavoritesRequest;


public interface FavoritesService extends IService<Favorites> {


    ResponseResult addFavorite(AddFavoritesRequest addFavoritesRequest);

    Page<Favorites> getFavorites(PageRequest pageRequest);

    ResponseResult deleteFavorites(Long merchandiseId);
}
