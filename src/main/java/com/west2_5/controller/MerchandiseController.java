package com.west2_5.controller;

import com.west2_5.common.ResponseResult;
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


    @PostMapping("/manage/add")
    public ResponseResult addMerchandise(@RequestBody AddMerchandiseRequest merchandise) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        merchandise.setSellerId(userId);
        merchandiseService.addMerchandise(merchandise);
        return ResponseResult.success();
    }

    @PostMapping("/manage/out")
    public ResponseResult outMerchandise(@RequestParam Long mid) {
        merchandiseService.outMerchandise(mid);
        return ResponseResult.success();
    }


    @GetMapping("/list/overview")
    public ResponseResult viewMerchandiseDetails(@RequestParam int page) {
        List<MerchandiseOverview> overviewList = merchandiseService.overviewMerchandise(page);
        return ResponseResult.success(overviewList);
    }

    @GetMapping("/list/publish")
    public ResponseResult getMyPublishedMerchandise(@RequestParam int page) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        List<MerchandiseOverview> overviewList = merchandiseService.getMyPublishedMerchandise(userId, page);
        return ResponseResult.success(overviewList);
    }

    @GetMapping("/list/out")
    public ResponseResult getMyOutMerchandise(@RequestParam int page) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        List<MerchandiseOverview> overviewList = merchandiseService.getMyOutMerchandise(userId, page);
        return ResponseResult.success(overviewList);
    }

    @GetMapping("/list/details")
    public ResponseResult viewMerchandiseDetails(@RequestParam Long mid) {
        MerchandiseDetails details = merchandiseService.getMerchandiseDetails(mid);
        return ResponseResult.success(details);
    }




}

