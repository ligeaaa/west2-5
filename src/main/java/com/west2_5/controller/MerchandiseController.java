package com.west2_5.controller;

import com.west2_5.common.ResponseCode;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;
import com.west2_5.service.MerchandiseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchandise")
public class MerchandiseController {


    @Autowired
    private MerchandiseService merchandiseService;


    @PostMapping("/add")
    public ResponseResult addMerchandise(@RequestBody AddMerchandiseRequest merchandise) {

        Long userId = null;
        try{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            userId = user.getUserId();
        }catch (Exception e){
            throw new BusinessException(ResponseCode.USER_NOT_LOGIN);
        }

        merchandise.setSellerId(userId);
        merchandiseService.addMerchandise(merchandise);
        return ResponseResult.success();
    }

    @GetMapping("/details")
    public ResponseResult viewMerchandiseDetails(@RequestParam Long mid) {
        MerchandiseDetails details = merchandiseService.getMerchandiseDetails(mid);
        return ResponseResult.success(details);
    }

    @GetMapping("/overview")
    public ResponseResult viewMerchandiseDetails(@RequestParam int page) {
        List<MerchandiseOverview> overviewList = merchandiseService.overviewMerchandise(page);
        return ResponseResult.success(overviewList);
    }

    @GetMapping("/publish")
    public ResponseResult getMyPublishedMerchandise(@RequestParam int page) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        List<MerchandiseOverview> overviewList = merchandiseService.getMyPublishedMerchandise(userId, page);
        return ResponseResult.success(overviewList);
    }

    @GetMapping("/out")
    public ResponseResult getMyOutMerchandise(@RequestParam int page) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        List<MerchandiseOverview> overviewList = merchandiseService.getMyOutMerchandise(userId, page);
        return ResponseResult.success(overviewList);
    }

    @PostMapping("/buy")
    public ResponseResult Merchandise(@RequestParam Long mid) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        merchandiseService.buyMerchandise(userId,mid);
        return ResponseResult.success();
    }


}

