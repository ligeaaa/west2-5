package com.west2_5.controller;

import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.response.favorites.FavoritesDetails;
import com.west2_5.service.FavoritesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;

/**
 * (Favorites)表控制层
 *
 * @author makejava
 * @since 2023-05-07 14:23:39
 */

@Slf4j
@RestController
@RequestMapping("/favor")
public class FavoritesController{

    @Autowired
    private FavoritesService favoritesService;

    /**
     * 添加收藏
     * @author Lige
     * @since 2023-06-01
     */
    @PostMapping("/user/add")
    private ResponseResult addFavorites(@RequestBody Long merchandiseId){
        if(merchandiseId == null){
            throw new BusinessException(NULL_ERROR, "缺少商品Id");
        }
        return favoritesService.addFavorite(merchandiseId);
    }

    /**
     * 取消收藏
     * @author Lige
     * @since 2023-06-01
     */
    @PostMapping("/user/deleteFavorites")
    private ResponseResult deleteFavorites(@RequestBody Long merchandiseId){
        return favoritesService.deleteFavorites(merchandiseId);
    }

    /**
     * 获得当前用户的收藏列表
     * @author Lige
     * @since 2023-06-01
     */
    @GetMapping("/user/getFavorites")
    private ResponseResult<List<FavoritesDetails>> getFavorites(@RequestBody PageRequest pageRequest){
        return ResponseResult.success(favoritesService.getFavorites(pageRequest));
    }

    /**
     * 确认收藏状态
     * @author Lige
     * @since 2023-06-03
     */
    @GetMapping("/user/checkFavorites")
    private ResponseResult<Boolean> checkFavorites(@RequestParam Long mid){
        if(mid ==null){
            throw new BusinessException(NULL_ERROR, "缺少商品Id");
        }
        return ResponseResult.success(favoritesService.checkFavorites(mid));
    }

}



