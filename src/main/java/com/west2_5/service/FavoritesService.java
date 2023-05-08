package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.model.entity.Favorites;

import java.util.List;


/**
 * (Favorites)表服务接口
 *
 * @author makejava
 * @since 2023-05-07 14:23:49
 */
public interface FavoritesService extends IService<Favorites> {

    BaseResponse addCollection(Long userId, Long merchandiseId, String alias, String groupName);

    BaseResponse<ErrorCode> deleteByUserId(Long userId);

    BaseResponse<ErrorCode> deleteByTaskId(Long merchandiseId);

    BaseResponse<Long> getCountByTaskId(Long merchandiseId);

    BaseResponse<Long> getCountByUserId(Long userId);

    BaseResponse<Boolean> checkFavorites(Long userId, Long merchandiseId);

    BaseResponse<List<String>> queryGroupName(Long merchandiseId);
}
