package com.west2_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.favorites.AddFavoritesRequest;
import com.west2_5.service.FavoritesService;
import com.west2_5.utils.SnowflakeUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
        //新建实体，用来新增记录
        Favorites favorites = new Favorites();

        //参数异常判断
        if (addFavoritesRequest == null){
            throw new BusinessException(NULL_ERROR);
        }else{
            BeanUtils.copyProperties(addFavoritesRequest, favorites);

            //获取当前用户
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user == null){
                throw new BusinessException(NULL_ERROR);
            }
            Long userId = user.getUserId();
            favorites.setUserId(userId);
            if (favorites.getUserId() == null || favorites.getMerchandiseId() == null){
                throw new BusinessException(NULL_ERROR);
            }
            //TODO 判断商品id是否存在
        }

        // 雪花算法生成 Id
        SnowflakeUtil idGenerator = new SnowflakeUtil(1, 1);
        long favoriteId = idGenerator.nextId();
        favorites.setFavoriteId(favoriteId);

        //新增记录
        favoritesService.save(favorites);

        return ResponseResult.success();
    }

    /**
     * 获得当前用户的收藏列表
     * @author Lige
     * @since 2023-06-01
     */
    @GetMapping("/user/getFavorites")
    private ResponseResult<Page<Favorites>> getFavorites(@RequestBody PageRequest pageRequest){
        //获取当前用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            throw new BusinessException(NULL_ERROR);
        }
        Long userId = user.getUserId();

        //只查询当前用户
        LambdaQueryWrapper<Favorites> lambdaQuery = new LambdaQueryWrapper();
        lambdaQuery.eq(Favorites::getUserId, userId);
        Page<Favorites> favoritesPage =
                favoritesService.page(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()), lambdaQuery);

        return ResponseResult.success(favoritesPage);
    }
}



