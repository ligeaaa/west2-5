package com.west2_5.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ErrorCode;
import com.west2_5.common.ResultUtils;
import com.west2_5.constants.QueryPageParam;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.request.merchandise.SelectMerchandiseRequest;
import com.west2_5.model.request.merchandise.UpdateMerchandiseById;
import com.west2_5.service.MerchandiseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.west2_5.common.ErrorCode.NULL_ERROR;
import static com.west2_5.common.ErrorCode.PARAMS_ERROR;


/**
 * (Merchandise)表控制层
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@RestController
@RequestMapping("merchandise")
public class MerchandiseController{


    @Autowired
    private MerchandiseService merchandiseService;

    //增
    @ApiOperation("增")
    @PostMapping("/add")
    public BaseResponse<ErrorCode> addMerchandise(@RequestBody AddMerchandiseRequest addMerchandiseRequest) {
        if (addMerchandiseRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        //发布者id
        Long userid = addMerchandiseRequest.getUserid();
        //商品名称
        String title = addMerchandiseRequest.getTitle();
        //所属tag，中间用英文逗号隔开
        String tag = addMerchandiseRequest.getTag();
        //外部展示图片，默认为内部展示图片的第一张
        String externaldisplaypicture = addMerchandiseRequest.getExternaldisplaypicture();
        //内部展示的所有图片
        String displaypictures = addMerchandiseRequest.getDisplaypictures();
        //价格
        Integer price = addMerchandiseRequest.getPrice();
        //简介
        String briefintroduction = addMerchandiseRequest.getBriefintroduction();

        BaseResponse result = merchandiseService.addMerchandise(userid, title, tag, externaldisplaypicture, displaypictures, price, briefintroduction);

        return result;
    }

    //删
    @ApiOperation("删")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteMerchandiseById(Long id) {
        if (id <= 0) {
            return ResultUtils.error(PARAMS_ERROR);
        }
        return ResultUtils.success(merchandiseService.removeById(id));
    }

    //改
    @ApiOperation("更新")
    @PostMapping("/update")
    public BaseResponse updateMerchandiseById(@RequestBody UpdateMerchandiseById updateMerchandiseById, HttpServletRequest request) {
        if (updateMerchandiseById == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        Long id = updateMerchandiseById.getId();
        //商品名称
        String title = updateMerchandiseById.getTitle();
        //所属tag，中间用英文逗号隔开
        String tag = updateMerchandiseById.getTag();
        //外部展示图片，默认为内部展示图片的第一张
        String externaldisplaypicture = updateMerchandiseById.getExternaldisplaypicture();
        //内部展示的所有图片
        String displaypictures = updateMerchandiseById.getDisplaypictures();
        //价格
        Integer price = updateMerchandiseById.getPrice();
        //简介
        String briefintroduction = updateMerchandiseById.getBriefintroduction();

        return merchandiseService.updateMerchandiseById(id, title, tag, externaldisplaypicture, displaypictures, price, briefintroduction);
    }

    //查(根据advice,contactDetails,feedbackUserId)
    @ApiOperation("查询")
    @PostMapping("/select")
    public BaseResponse<List<Merchandise>> selectMerchandiseId(@RequestBody SelectMerchandiseRequest selectMerchandiseRequest, HttpServletRequest request) {
        //判断是否为空
        if (selectMerchandiseRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        //发布者id
        Long userid = selectMerchandiseRequest.getUserid();
        //商品名称
        String title = selectMerchandiseRequest.getTitle();
        //所属tag，中间用英文逗号隔开
        String tag = selectMerchandiseRequest.getTag();
        //价格
        Integer price = selectMerchandiseRequest.getPrice();
        //简介
        String briefintroduction = selectMerchandiseRequest.getBriefintroduction();

        Long pageNum = selectMerchandiseRequest.getCurrent();
        Long pageSize = selectMerchandiseRequest.getPageSize();
        QueryPageParam queryPageParam = new QueryPageParam();

        LambdaQueryWrapper<Merchandise> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (userid != null) {
            lambdaQueryWrapper.like(Merchandise::getUserid, userid);
        }

        if (title != null) {
            lambdaQueryWrapper.like(Merchandise::getTitle, title);
        }

        if (tag != null) {
            lambdaQueryWrapper.like(Merchandise::getTag, tag);
        }

        if (price != null) {
            lambdaQueryWrapper.like(Merchandise::getPrice, price);
        }

        if (briefintroduction != null) {
            lambdaQueryWrapper.like(Merchandise::getBriefintroduction, briefintroduction);
        }

        if (pageNum != null) {
            // queryPageParam.setPageNum(pageNum);
        }

        if (pageSize != null) {
            // queryPageParam.setPageSize(pageSize);
        }
        Page<Merchandise> page = new Page();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        IPage<Merchandise> result = merchandiseService.page(page, lambdaQueryWrapper);

        return ResultUtils.success(result.getRecords());
    }
}

