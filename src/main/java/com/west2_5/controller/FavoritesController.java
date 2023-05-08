package com.west2_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.constants.QueryPageParam;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.request.collection.AddCollectionRequest;
import com.west2_5.model.request.collection.SelectCollectionRequest;
import com.west2_5.service.FavoritesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

import static com.west2_5.common.ErrorCode.NULL_ERROR;
import static com.west2_5.common.ErrorCode.PARAMS_ERROR;

/**
 * (Favorites)表控制层
 *
 * @author makejava
 * @since 2023-05-07 14:23:39
 */
@RestController
@RequestMapping("favorites")
public class FavoritesController{

    @Resource
    private FavoritesService favoritesService;

    // region 增删改查

    //增
    @PostMapping("/add")
    public BaseResponse<ErrorCode> addCollection(@RequestBody AddCollectionRequest addCollectionRequest) {
        System.out.println("1111111111111111111111");
        if (addCollectionRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        Long userId = addCollectionRequest.getUserId();
        Long taskId = addCollectionRequest.getTaskId();
        String alias = addCollectionRequest.getAlias();
        String groupName = addCollectionRequest.getGroupName();

        BaseResponse result = favoritesService.addCollection(userId, taskId, alias, groupName);

        return result;
    }


    //删
    @ApiOperation("通过Id进行删除")
    @DeleteMapping("/deleteById")
    @ApiImplicitParam(name = "id", dataTypeClass = Integer.class, required = true)
    public BaseResponse<Boolean> deleteCollectionById(Integer id, HttpServletRequest request) {
        if (id <= 0) {
            return ResultUtils.error(PARAMS_ERROR);
        }
        return ResultUtils.success(favoritesService.removeById(id));
    }

    //改
    @ApiOperation("更新")
    @PutMapping("/update")
    @ApiImplicitParam(name = "favorites",type = "body", dataTypeClass = Favorites.class, required = true)
    public BaseResponse<Boolean> updateCollectionById(@RequestBody Favorites favorites) {
        return ResultUtils.success(favoritesService.updateById(favorites));
    }

    //查(根据userid和taskid）
    @ApiOperation("查询")
    @PostMapping("/select")
    @ApiImplicitParam(name = "SelectCollectionRequest",type = "body", dataTypeClass = SelectCollectionRequest.class, required = true)
    public BaseResponse<List<Favorites>> selectCollectionById(@RequestBody SelectCollectionRequest selectCollectionRequest) {
        //判断是否为空
        if (selectCollectionRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        Long userId = selectCollectionRequest.getUserId();
        Long merchandiseId = selectCollectionRequest.getMerchandiseId();
        String alias = selectCollectionRequest.getAlias();
        String groupName = selectCollectionRequest.getGroupName();
        Long current = selectCollectionRequest.getCurrent();
        Long pageSize = selectCollectionRequest.getPageSize();
        QueryPageParam queryPageParam = new QueryPageParam();


        LambdaQueryWrapper<Favorites> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (userId != null) {
            lambdaQueryWrapper.eq(Favorites::getUserId, userId);
        }

        if (merchandiseId != null) {
            lambdaQueryWrapper.eq(Favorites::getMerchandiseId, merchandiseId);
        }

        queryPageParam.setPageNum(current);

        queryPageParam.setPageSize(pageSize);

        if (alias != null) {
            lambdaQueryWrapper.eq(Favorites::getAlias, alias);
        }

        if (groupName != null) {
            lambdaQueryWrapper.eq(Favorites::getGroupName, groupName);
        }

        Page<Favorites> page = new Page();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());
        page.setOptimizeCountSql(false);

        IPage<Favorites> result = favoritesService.page(page, lambdaQueryWrapper);

        System.out.println(result.getTotal());
        return ResultUtils.success(result.getRecords());
    }

    // endregion

    /**
     * 通过id进行查询
     *
     * @author Lige
     * @since 2023-04-14
     */
    @GetMapping("getById")
    public BaseResponse<Favorites> getFavoritesById(Integer id){
        Favorites favorites = favoritesService.getById(id);
        return ResultUtils.success(favorites);
    }

    /**
     * 通过用户id进行删除
     *
     * @author Lige
     * @since 2023-04-14
     */
    @ApiOperation("通过UserId进行删除")
    @DeleteMapping ("/deleteByUserId")
    @ApiImplicitParam(name = "userId", dataTypeClass = Integer.class, required = true)
    public BaseResponse<ErrorCode> deleteCollectionByUserId(Long userId) {
        if (userId == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        if (userId <= 0) {
            return ResultUtils.error(PARAMS_ERROR);
        }
        return favoritesService.deleteByUserId(userId);
    }

    /**
     * 通过任务id进行删除
     *
     * @author Lige
     * @since 2023-04-14
     */
    @ApiOperation("通过TaskId进行删除")
    @DeleteMapping ("/deleteByTaskId")
    @ApiImplicitParam(name = "taskId", dataTypeClass = Integer.class, required = true)
    public BaseResponse<ErrorCode> deleteCollectionByTaskId(Long taskId, HttpServletRequest request) {
        if (taskId == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        if (taskId <= 0) {
            return ResultUtils.error(PARAMS_ERROR);
        }
        return favoritesService.deleteByTaskId(taskId);
    }

    //TODO 按照时间顺序显示收藏

    //TODO 显示某任务收藏总数
    /**
     * 显示某任务收藏总数
     *
     * @author Lige
     * @since 2023-04-14
     */
    @GetMapping("getCountByTaskId")
    public BaseResponse<Long> getCountByTaskId(Long taskId){
        return favoritesService.getCountByTaskId(taskId);
    }

    //TODO 显示某人收藏了多少任务
    /**
     * 显示某人收藏了多少任务
     *
     * @author Lige
     * @since 2023-04-14
     */
    @GetMapping("getCountByUserId")
    public BaseResponse<Long> getCountByUserId(Long userId){
        return favoritesService.getCountByUserId(userId);
    }

    //TODO 判断该任务是否已存在
    /**
     * 判断该任务是否已存在
     *
     * @author Lige
     * @since 2023-04-14
     */
    @GetMapping("/checkFavorites")
    public BaseResponse<Boolean> checkFavorites(Long userId, Long taskId){
        return favoritesService.checkFavorites(userId, taskId);
    }

    //TODO 查询现有组别
    /**
     * 查询现有组别
     *
     * @author Lige
     * @since 2023-04-14
     */
    @GetMapping("/queryGroupName")
    public BaseResponse<List<String>> queryGroupName(Long userId){
        return favoritesService.queryGroupName(userId);
    }

    //TODO 新建组

    //TODO 根据组别查询收藏

    //TODO updateTime记得添加
}

