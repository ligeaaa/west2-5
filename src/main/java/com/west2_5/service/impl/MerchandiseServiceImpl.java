package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseCode;
import com.west2_5.exception.BusinessException;
import com.west2_5.mapper.MerchandiseImgMapper;
import com.west2_5.mapper.MerchandiseMapper;
import com.west2_5.mapper.TagMapper;
import com.west2_5.mapper.UserMapper;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.MerchandiseImg;
import com.west2_5.model.entity.Tag;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;
import com.west2_5.service.MerchandiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/** 
 * @Date: 2023/5/31
 * @Author: RuiLin
 * @Description:
 */

@Slf4j
@Service("merchandiseService")
public class MerchandiseServiceImpl extends ServiceImpl<MerchandiseMapper, Merchandise> implements MerchandiseService {

    @Resource
    private MerchandiseMapper merchandiseMapper;

    @Resource
    private MerchandiseImgMapper merchandiseImgMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void addMerchandise(AddMerchandiseRequest merchandiseRequest) {

        Merchandise merchandise = new Merchandise();
        BeanUtils.copyProperties(merchandiseRequest, merchandise);

        merchandiseMapper.insert(merchandise);
        Long merchandiseId = merchandise.getMerchandiseId();

        List<String> pictures = merchandiseRequest.getPictures();

        int priority = 1;

        if (pictures.size() == 0) {
            throw new BusinessException(ResponseCode.NULL_ERROR, "至少要有一张图片");
        }

        for (String url : pictures) {
            MerchandiseImg img = new MerchandiseImg();
            img.setMerchandiseId(merchandiseId);
            img.setImgUrl(url);
            img.setImgPriority(priority);
            merchandiseImgMapper.insert(img);
            priority++;
        }

    }

    @Override
    public MerchandiseDetails getMerchandiseDetails(Long merchandiseId) {

        LambdaQueryWrapper<Merchandise> merchandiseQuery = new LambdaQueryWrapper();
        merchandiseQuery.eq(Merchandise::getMerchandiseId, merchandiseId);
        Merchandise merchandise = merchandiseMapper.selectOne(merchandiseQuery);

        Long tagId = merchandise.getTagId();
        LambdaQueryWrapper<Tag> tagQuery = new LambdaQueryWrapper();
        tagQuery.eq(Tag::getTagId, tagId);
        Tag tag = tagMapper.selectOne(tagQuery);

        LambdaQueryWrapper<MerchandiseImg> imgQuery = new LambdaQueryWrapper();
        imgQuery.eq(MerchandiseImg::getMerchandiseId, merchandiseId);
        List<MerchandiseImg> images = merchandiseImgMapper.selectList(imgQuery);
        List<String> imgUrls = new ArrayList<>();
        for(MerchandiseImg image: images){
            imgUrls.add(image.getImgUrl());
        }

        Long sellerId = merchandise.getSellerId();
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper();
        userQuery.eq(User::getUserId, sellerId);
        User seller = userMapper.selectOne(userQuery);

        MerchandiseDetails details = new MerchandiseDetails();
        BeanUtils.copyProperties(merchandise, details);
        BeanUtils.copyProperties(tag, details);
        BeanUtils.copyProperties(seller, details);
        details.setPictures(imgUrls);

        return details;
    }

    @Override
    public List<MerchandiseOverview> overviewMerchandise(int currentPage) {

        List<MerchandiseOverview> overviewList = new ArrayList<>();

        Page<Merchandise> page = new Page<>(currentPage, 4);
        LambdaQueryWrapper<Merchandise> merchandiseQuery = new LambdaQueryWrapper();
        merchandiseQuery.eq(Merchandise::getStatus, 1);
        IPage<Merchandise> resultPage = merchandiseMapper.selectPage(page, merchandiseQuery);
        List<Merchandise> merchandises = resultPage.getRecords();

        List<String> imgUrls = new ArrayList<>();
        for(Merchandise m: merchandises){
            MerchandiseOverview overview = new MerchandiseOverview();
            BeanUtils.copyProperties(m, overview);

            Long merchandiseId = m.getMerchandiseId();
            LambdaQueryWrapper<MerchandiseImg> imgQuery = new LambdaQueryWrapper();
            imgQuery.eq(MerchandiseImg::getMerchandiseId, merchandiseId)
                    .eq(MerchandiseImg::getImgPriority,1);
            MerchandiseImg image = merchandiseImgMapper.selectOne(imgQuery);
            overview.setImgCover(image.getImgUrl());
            overviewList.add(overview);
        }

        return overviewList;
    }

}

