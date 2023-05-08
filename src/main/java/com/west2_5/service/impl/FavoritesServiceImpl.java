package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.mapper.FavoritesMapper;
import com.west2_5.model.entity.Favorites;
import com.west2_5.service.FavoritesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.west2_5.common.ErrorCode.*;

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

    @Override
    public BaseResponse<ErrorCode> addCollection(Long user_id, Long merchandise_id, String alias, String groupName) {
        if (user_id == null || merchandise_id == null){
            return ResultUtils.error(NULL_ERROR,"数据为空");
        }
        //TODO 判断用户是否存在

        //TODO 判断任务是否存在
//        BaseResponse<Task> task = taskController.getTaskById(user_id);
//        if (task.getData() == null){
//            return ResultUtils.error(PARAMS_ERROR,"该用户不存在");
//        }

        //判断该收藏关系是否已经存在

        if(favoritesService.checkFavorites(user_id,merchandise_id).getData()){
            return ResultUtils.error(PARAMS_ERROR,"该收藏已存在");
        }

        //实现add功能
        Favorites favorites = new Favorites();

        LocalDateTime localDateTime = LocalDateTime.now();
        favorites.setUserId(user_id);
        favorites.setMerchandiseId(merchandise_id);
        favorites.setCreateTime(localDateTime);
        favorites.setUpdateTime(localDateTime);

        if (groupName != null){
            favorites.setGroupName(groupName);
        }else {
            favorites.setGroupName("默认分组");
        }

        if (alias != null){
            favorites.setAlias(alias);
        }else {
            // TODO 根据商品id获取商品title
//            String Title = merchandiseService.getById(merchandise_id).getTitle();
//            favorites.setAlias(Title);
        }

        boolean saveResult = this.save(favorites);
        if (saveResult){
            return ResultUtils.success(SUCCESS);
        }else{
            return ResultUtils.error(SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse<ErrorCode> deleteByUserId(Long userId) {
        //TODO 判断userId是否存在

        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Favorites::getUserId,userId);
        Boolean deleteResult = remove(queryWrapper);

        if (deleteResult){
            return ResultUtils.success(SUCCESS);
        }else{
            return ResultUtils.error(SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse<ErrorCode> deleteByTaskId(Long merchandiseId) {
        //判断taskId是否存在

        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Favorites::getMerchandiseId,merchandiseId);
        Boolean deleteResult = remove(queryWrapper);

        if (deleteResult){
            return ResultUtils.success(SUCCESS);
        }else{
            return ResultUtils.error(SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse<Boolean> checkFavorites(Long userId, Long merchandiseId) {
        LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoritesLambdaQueryWrapper.eq(Favorites::getUserId, userId);
        favoritesLambdaQueryWrapper.eq(Favorites::getMerchandiseId, merchandiseId);

        Long count = (long)favoritesService.count(favoritesLambdaQueryWrapper);
        return ResultUtils.success(count != 0);
    }

    @Override
    public BaseResponse<Long> getCountByTaskId(Long merchandiseId) {
        //TODO 判断taskId是否存在且合法

        LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoritesLambdaQueryWrapper.eq(Favorites::getMerchandiseId, merchandiseId);
        Long count = (long)favoritesService.count(favoritesLambdaQueryWrapper);
        return ResultUtils.success(count);
    }

    @Override
    public BaseResponse<Long> getCountByUserId(Long userId) {
        //TODO 判断userId是否存在且合法

        LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoritesLambdaQueryWrapper.eq(Favorites::getUserId, userId);
        Long count = (long)favoritesService.count(favoritesLambdaQueryWrapper);
        return ResultUtils.success(count);
    }

    @Override
    public BaseResponse<List<String>> queryGroupName(Long userId) {

        //查询该userId的所有收藏
        LambdaQueryWrapper<Favorites> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorites::getUserId,userId);
        List<Favorites> favoritesList = favoritesService.list(lambdaQueryWrapper);

        //获取这些收藏的groupName并去重
        Set<String> groupName = favoritesList.stream()
                .map(favorites -> favorites.getGroupName())
                .collect(Collectors.toSet());
        List<String> groupNameList = new ArrayList<>(groupName);

        //TODO 要按照什么顺序输出

        return ResultUtils.success(groupNameList);
    }
}

