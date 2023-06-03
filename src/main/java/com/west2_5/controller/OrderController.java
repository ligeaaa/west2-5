package com.west2_5.controller;

import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.User;
import com.west2_5.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping("/manage/create")
    public ResponseResult Merchandise(@RequestParam Long mid) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();
        orderService.createOrder(userId, mid);
        return ResponseResult.success();
    }

}
