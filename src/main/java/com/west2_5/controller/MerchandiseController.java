package com.west2_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.west2_5.common.ResponseCode;
import com.west2_5.common.ResponseResult;
import com.west2_5.constants.QueryPageParam;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.request.merchandise.SelectMerchandiseRequest;
import com.west2_5.service.MerchandiseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.west2_5.common.ResponseCode.*;

@RestController
@RequestMapping("/merchandise")
public class MerchandiseController{


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

