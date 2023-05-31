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
import com.west2_5.service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.west2_5.common.ErrorCode.*;

@RestController
@RequestMapping("/merchandise")
public class MerchandiseController{


    @Autowired
    private MerchandiseService merchandiseService;

    @PostMapping("/add")
    public BaseResponse<ErrorCode> addMerchandise(@RequestBody AddMerchandiseRequest addMerchandiseRequest) {
        if (addMerchandiseRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }


        Long userid = addMerchandiseRequest.getUserid();

        String title = addMerchandiseRequest.getTitle();

        String tag = addMerchandiseRequest.getTag();
        //外部展示图片，默认为内部展示图片的第一张
        String externaldisplaypicture = addMerchandiseRequest.getExternaldisplaypicture();
        //内部展示的所有图片
        String displaypictures = addMerchandiseRequest.getDisplaypictures();
        //价格
        Integer price = addMerchandiseRequest.getPrice();
        //简介
        String briefintroduction = addMerchandiseRequest.getBriefintroduction();

        return ResultUtils.success(SUCCESS);
    }


    @PostMapping("/select")
    public BaseResponse<List<Merchandise>> selectMerchandise(@RequestBody SelectMerchandiseRequest selectMerchandiseRequest, HttpServletRequest request) {
        if (selectMerchandiseRequest == null) {
            return ResultUtils.error(NULL_ERROR);
        }

        Long sellerId = selectMerchandiseRequest.getUserid();
        String title = selectMerchandiseRequest.getTitle();
        String tag = selectMerchandiseRequest.getTag();
        Double price = selectMerchandiseRequest.getPrice();
        String introduction = selectMerchandiseRequest.getIntroduction();

        Long current = selectMerchandiseRequest.getCurrent();
        Long pageSize = selectMerchandiseRequest.getPageSize();
        QueryPageParam queryPageParam = new QueryPageParam();

        LambdaQueryWrapper<Merchandise> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (sellerId != null) {
            lambdaQueryWrapper.like(Merchandise::getSellerId, sellerId);
        }

        if (title != null) {
            lambdaQueryWrapper.like(Merchandise::getTitle, title);
        }

        if (tag != null) {
            lambdaQueryWrapper.like(Merchandise::getTagId, tag);
        }


        if (price != null) {
            lambdaQueryWrapper.like(Merchandise::getPrice, price);
        }

        if (introduction != null) {
            lambdaQueryWrapper.like(Merchandise::getIntroduction, introduction);
        }

        //current默认为20，pageSize默认为1

        queryPageParam.setPageNum(current);

        queryPageParam.setPageSize(pageSize);

        Page<Merchandise> page = new Page();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());
        IPage<Merchandise> result = merchandiseService.page(page, lambdaQueryWrapper);
        return ResultUtils.success(result.getRecords());
    }


}

