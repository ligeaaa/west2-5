package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.BaseResponse;
import com.west2_5.common.ResultUtils;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.mapper.MerchandiseMapper;
import com.west2_5.service.MerchandiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.west2_5.common.ErrorCode.*;

/**
 * (Merchandise)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("merchandiseService")
public class MerchandiseServiceImpl extends ServiceImpl<MerchandiseMapper, Merchandise> implements MerchandiseService {

    @Resource
    private MerchandiseMapper merchandiseMapper;

    @Override
    public BaseResponse addMerchandise(Long userid, String title, String tag, String pictures, double price, String introduction) {


        Merchandise merchandise = new Merchandise();

        merchandise.setSellerId(userid);
        merchandise.setTitle(title);
        if (tag == null){
            merchandise.setTagId(null);
        }
        if (pictures == null){
            merchandise.setPictures("null");
        }

        merchandise.setPrice(price);
        if (introduction == null){
            merchandise.setIntroduction("这里没有简介哦~");
        }

        merchandise.setStatus(2);


        boolean saveResult = this.save(merchandise);
        if (saveResult){
            return ResultUtils.success(SUCCESS);
        }else{
            return ResultUtils.error(SYSTEM_ERROR);
        }
    }

}

