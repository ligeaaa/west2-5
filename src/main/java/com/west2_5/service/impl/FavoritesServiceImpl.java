package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.mapper.FavoritesMapper;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.favorites.AddFavoritesRequest;
import com.west2_5.model.response.favorites.FavoritesDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;
import com.west2_5.model.response.user.UserBasicInfo;
import com.west2_5.service.FavoritesService;
import com.west2_5.service.MerchandiseService;
import com.west2_5.service.UserService;
import com.west2_5.utils.SnowflakeUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;
import static com.west2_5.common.ResponseCode.USER_NOT_LOGIN;

/**
 * (Favorites)表服务实现类
 *
 * @author makejava
 * @since 2023-05-07 14:23:49
 */
@Service("favoritesService")
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {

    @Resource
    private FavoritesService favoritesService;

    @Resource
    private MerchandiseService merchandiseService;

    @Resource
    private UserService userService;

    /**
     * 添加收藏
     * @author Lige
     * @since 2023-06-01
     */
    @Override
    public ResponseResult addFavorite(AddFavoritesRequest addFavoritesRequest) {

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

        //TODO 确认收藏是否已经存在，若存在则变成删除

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
    @Override
    public List<FavoritesDetails> getFavorites(PageRequest pageRequest) {
        //获取当前用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        //只查询当前用户
        LambdaQueryWrapper<Favorites> lambdaQuery = new LambdaQueryWrapper();
        lambdaQuery.eq(Favorites::getUserId, userId);
        Page<Favorites> favoritesPage =
                favoritesService.page(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()), lambdaQuery);

        List<FavoritesDetails> favoritesDetailLists = new ArrayList<>();
        List<Favorites> favorites = favoritesPage.getRecords();
        for (Favorites favorites1 : favorites) {
            FavoritesDetails favoritesDetails = new FavoritesDetails();
            BeanUtils.copyProperties(favorites1, favoritesDetails);

            //商品基本信息的获取
            Merchandise merchandise = merchandiseService.getByMerchandiseId(favoritesDetails.getMerchandiseId());
            MerchandiseOverview merchandiseOverview = new MerchandiseOverview();
            BeanUtils.copyProperties(merchandise, merchandiseOverview);

            //用户基本信息的获取
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            BeanUtils.copyProperties(user, userBasicInfo);

            //存入response中
            favoritesDetails.setMerchandiseOverview(merchandiseOverview);
            favoritesDetails.setUserBasicInfo(userBasicInfo);

            //加入列表
            favoritesDetailLists.add(favoritesDetails);
        }

        return favoritesDetailLists;
    }

    /**
     * 取消收藏
     * @author Lige
     * @since 2023-06-01
     */
    @Override
    public ResponseResult deleteFavorites(Long merchandiseId) {
        //获取当前用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        //删除条件
        LambdaQueryWrapper<Favorites> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorites::getUserId, userId);
        lambdaQueryWrapper.eq(Favorites::getMerchandiseId, merchandiseId);

        //执行操作
        remove(lambdaQueryWrapper);

        return ResponseResult.success();
    }

    /**
     * 确认收藏状态
     * @author Lige
     * @since 2023-06-03
     */
    @Override
    public Boolean checkFavorites(Long merchandiseId) {
        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        LambdaQueryWrapper<Favorites> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorites::getUserId, userId);
        lambdaQueryWrapper.eq(Favorites::getMerchandiseId, merchandiseId);
        Favorites favorites = getOne(lambdaQueryWrapper);

        if (favorites == null){
            return false;
        } else{
            return true;
        }

    }
}

