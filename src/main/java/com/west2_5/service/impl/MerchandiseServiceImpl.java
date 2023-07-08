package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseCode;
import com.west2_5.exception.BusinessException;
import com.west2_5.mapper.MerchandiseImgMapper;
import com.west2_5.mapper.MerchandiseMapper;
import com.west2_5.mapper.TagMapper;
import com.west2_5.mapper.UserMapper;
import com.west2_5.model.entity.*;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;
import com.west2_5.service.MerchandiseService;
import com.west2_5.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.west2_5.constants.MerchandiseConstant.TAKEN_OFF;

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

    @Resource
    private OrdersService orderService;

    //根据商品Id获取商品
    @Override
    public Merchandise getByMerchandiseId(Long merchandiseId) {
        LambdaQueryWrapper<Merchandise> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Merchandise::getMerchandiseId, merchandiseId);
        Merchandise merchandise = getOne(lambdaQueryWrapper);
        if(merchandise == null){
            throw new BusinessException(ResponseCode.NULL_ERROR,"商品不存在");
        }
        return merchandise;
    }

    //查看商品详情
    @Override
    public MerchandiseDetails getMerchandiseDetails(Long merchandiseId) {

        Merchandise merchandise = getByMerchandiseId(merchandiseId);
        Long tagId = merchandise.getTagId();
        LambdaQueryWrapper<Tag> tagQuery = new LambdaQueryWrapper();
        tagQuery.eq(Tag::getTagId, tagId);
        Tag tag = tagMapper.selectOne(tagQuery);

        LambdaQueryWrapper<MerchandiseImg> imgQuery = new LambdaQueryWrapper();
        imgQuery.eq(MerchandiseImg::getMerchandiseId, merchandiseId);
        List<MerchandiseImg> images = merchandiseImgMapper.selectList(imgQuery);
        List<String> imgUrls = new ArrayList<>();
        for (MerchandiseImg image : images) {
            imgUrls.add(image.getImgUrl());
        }

        Long sellerId = merchandise.getSellerId();
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper();
        userQuery.eq(User::getUserId, sellerId);
        User seller = userMapper.selectOne(userQuery);

        MerchandiseDetails details = new MerchandiseDetails();

        //FIXME: tag和merchandise的实体类都有state，复制时会被覆盖，暂时先调整顺序，先复制tag再复制商品放最后（优化回头看看
        BeanUtils.copyProperties(tag, details);
        BeanUtils.copyProperties(seller, details);
        BeanUtils.copyProperties(merchandise, details);
        details.setPictures(imgUrls);

        return details;
    }

    //TODO
    //查看商品概览
    @Override
    public MerchandiseOverview getOverviewMerchandise(Long merchandiseId) {
        MerchandiseOverview overview = new MerchandiseOverview();
        return overview;
    }

    //首页商品概览列表
    @Override
    public List<MerchandiseOverview> getOverviewMerchandiseList(int currentPage) {

        List<MerchandiseOverview> overviewList = new ArrayList<>();

        Page<Merchandise> page = new Page<>(currentPage, 4);
        LambdaQueryWrapper<Merchandise> merchandiseQuery = new LambdaQueryWrapper();
        merchandiseQuery.eq(Merchandise::getState, 0); //获取已上架商品
        IPage<Merchandise> resultPage = merchandiseMapper.selectPage(page, merchandiseQuery);
        List<Merchandise> merchandises = resultPage.getRecords();

        List<String> imgUrls = new ArrayList<>();
        for (Merchandise m : merchandises) {
            MerchandiseOverview overview = new MerchandiseOverview();
            BeanUtils.copyProperties(m, overview);

            Long merchandiseId = m.getMerchandiseId();
            LambdaQueryWrapper<MerchandiseImg> imgQuery = new LambdaQueryWrapper();
            imgQuery.eq(MerchandiseImg::getMerchandiseId, merchandiseId).eq(MerchandiseImg::getImgPriority, 1);
            MerchandiseImg image = merchandiseImgMapper.selectOne(imgQuery);
            overview.setPicture(image.getImgUrl());
            overviewList.add(overview);
        }

        return overviewList;
    }

    //获取我发布的商品
    @Override
    public List<MerchandiseOverview> getMyPublishedMerchandise(Long userId, int currentPage) {
        List<MerchandiseOverview> overviewList = new ArrayList<>();

        Page<Merchandise> page = new Page<>(currentPage, 4);
        LambdaQueryWrapper<Merchandise> merchandiseQuery = new LambdaQueryWrapper();
        merchandiseQuery.eq(Merchandise::getSellerId, userId).eq(Merchandise::getState, 0); //相当于比首页商品多了这行【只搜用户自己的商品列表】

        IPage<Merchandise> resultPage = merchandiseMapper.selectPage(page, merchandiseQuery);
        List<Merchandise> merchandises = resultPage.getRecords();

        List<String> imgUrls = new ArrayList<>();
        for (Merchandise m : merchandises) {
            MerchandiseOverview overview = new MerchandiseOverview();
            BeanUtils.copyProperties(m, overview);
            overview.setState("已发布"); //还有这行

            Long merchandiseId = m.getMerchandiseId();
            LambdaQueryWrapper<MerchandiseImg> imgQuery = new LambdaQueryWrapper();
            imgQuery.eq(MerchandiseImg::getMerchandiseId, merchandiseId).eq(MerchandiseImg::getImgPriority, 1);
            MerchandiseImg image = merchandiseImgMapper.selectOne(imgQuery);
            overview.setPicture(image.getImgUrl());
            overviewList.add(overview);
        }

        return overviewList;
    }

    //获取我下架的商品
    @Override
    public List<MerchandiseOverview> getMyOutMerchandise(Long userId, int currentPage) {
        List<MerchandiseOverview> overviewList = new ArrayList<>();

        Page<Merchandise> page = new Page<>(currentPage, 4);
        LambdaQueryWrapper<Merchandise> merchandiseQuery = new LambdaQueryWrapper();
        merchandiseQuery.eq(Merchandise::getSellerId, userId).eq(Merchandise::getState, 1); //

        IPage<Merchandise> resultPage = merchandiseMapper.selectPage(page, merchandiseQuery);
        List<Merchandise> merchandises = resultPage.getRecords();

        List<String> imgUrls = new ArrayList<>();
        for (Merchandise m : merchandises) {
            MerchandiseOverview overview = new MerchandiseOverview();
            BeanUtils.copyProperties(m, overview);
            overview.setState("已下架");

            Long merchandiseId = m.getMerchandiseId();
            LambdaQueryWrapper<MerchandiseImg> imgQuery = new LambdaQueryWrapper();
            imgQuery.eq(MerchandiseImg::getMerchandiseId, merchandiseId).eq(MerchandiseImg::getImgPriority, 1);
            MerchandiseImg image = merchandiseImgMapper.selectOne(imgQuery);
            overview.setPicture(image.getImgUrl());
            overviewList.add(overview);
        }
        
        return overviewList;
    }


    //发布商品
    @Override
    public Long addMerchandise(AddMerchandiseRequest merchandiseRequest) {

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
        return merchandiseId;
    }

    //下架商品
    @Override
    public Merchandise outMerchandise(Long merchandiseId) {
        Merchandise merchandise = getByMerchandiseId(merchandiseId);
        merchandise.setState(TAKEN_OFF);
        updateById(merchandise);
        return merchandise;
    }

}

