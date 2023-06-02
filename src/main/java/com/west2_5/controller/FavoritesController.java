package com.west2_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.favorites.AddFavoritesRequest;
import com.west2_5.model.response.favorites.FavoritesDetails;
import com.west2_5.service.FavoritesService;
import com.west2_5.utils.SnowflakeUtil;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;

/**
 * (Favorites)表控制层
 *
 * @author makejava
 * @since 2023-05-07 14:23:39
 */
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
    private ResponseResult addFavorites(@RequestBody AddFavoritesRequest addFavoritesRequest){
        return favoritesService.addFavorite(addFavoritesRequest);
    }

    /**
     * 取消收藏
     * @author Lige
     * @since 2023-06-01
     */
    @DeleteMapping("/user/deleteFavorites")
    private ResponseResult deleteFavorites(Long merchandiseId){
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
    private ResponseResult<Boolean> checkFavorites(Long merchandiseId){
        return ResponseResult.success(favoritesService.checkFavorites(merchandiseId));
    }

}



