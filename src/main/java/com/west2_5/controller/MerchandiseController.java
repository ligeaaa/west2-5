package com.west2_5.controller;

import com.west2_5.common.ResponseCode;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.service.MerchandiseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/merchandise")
public class MerchandiseController {


    @Autowired
    private MerchandiseService merchandiseService;


    @PostMapping("/add")
    public ResponseResult addMerchandise(@RequestBody AddMerchandiseRequest merchandise) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        merchandise.setSellerId(userId);
        merchandiseService.addMerchandise(merchandise);
        return ResponseResult.success();
    }



}

