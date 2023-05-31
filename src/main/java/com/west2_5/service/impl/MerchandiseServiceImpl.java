package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseCode;
import com.west2_5.exception.BusinessException;
import com.west2_5.mapper.MerchandiseImgMapper;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.mapper.MerchandiseMapper;
import com.west2_5.model.entity.MerchandiseImg;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.service.MerchandiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

import java.util.List;


@Slf4j
@Service("merchandiseService")
public class MerchandiseServiceImpl extends ServiceImpl<MerchandiseMapper, Merchandise> implements MerchandiseService {

    @Resource
    private MerchandiseMapper merchandiseMapper;

    @Resource
    private MerchandiseImgMapper merchandiseImgMapper;

    @Override
    public void addMerchandise(AddMerchandiseRequest merchandiseRequest) {

         Merchandise  merchandise = new Merchandise();
         BeanUtils.copyProperties(merchandiseRequest,merchandise);

        merchandiseMapper.insert(merchandise);
        Long merchandiseId = merchandise.getMerchandiseId();

        List<String> pictures = merchandiseRequest.getPictures();

        int priority = 1;
        for(String url: pictures){
            MerchandiseImg img = new MerchandiseImg();
            img.setMerchandiseId(merchandiseId);
            img.setImgUrl(url);
            img.setImgPriority(priority);
            merchandiseImgMapper.insert(img);
            priority++;
        }

    }

}

