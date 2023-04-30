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
    public BaseResponse addMerchandise(Long userid, String title, String tag, String externaldisplaypicture, String displaypictures, Integer price, String briefintroduction) {
        if (userid == null || title == null || price == null){
            return ResultUtils.error(PARAMS_ERROR);
        }
        //实现add功能
        Merchandise merchandise = new Merchandise();

        //TODO 判断userid是否存在

        LocalDateTime localDateTime = LocalDateTime.now();
        merchandise.setUserid(userid);
        merchandise.setTitle(title);
        if (tag == null){
            merchandise.setTag("null");
        }
        if (displaypictures == null){
            merchandise.setDisplaypictures("null");
        }
        if (externaldisplaypicture == null){
            if (displaypictures == null){
                merchandise.setExternaldisplaypicture("null");
            }else{
                //TODO 添加默认图片路径
                merchandise.setExternaldisplaypicture("默认");
            }
        }
        merchandise.setPrice(price);
        if (briefintroduction == null){
            merchandise.setBriefintroduction("这里没有简介哦~");
        }
        merchandise.setStatu(2);
        merchandise.setCreateTime(localDateTime);
        merchandise.setUpdateTime(localDateTime);

        boolean saveResult = this.save(merchandise);
        if (saveResult){
            return ResultUtils.success(SUCCESS);
        }else{
            return ResultUtils.error(SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse updateMerchandiseById(Long id, String title, String tag, String externaldisplaypicture, String displaypictures, Integer price, String briefintroduction) {
        //判断为空,判断id是否合法,判断id是否存在
        if (id == null || id <= 0 || this.getById(id) == null){
            return ResultUtils.error(PARAMS_ERROR);
        }

        UpdateWrapper<Merchandise> updateWrapper = new UpdateWrapper<>();
        if (id != null){
            updateWrapper.lambda().set(Merchandise::getId,id);
        }
        if (title != null){
            updateWrapper.lambda().set(Merchandise::getTitle,title);
        }
        if (tag != null){
            updateWrapper.lambda().set(Merchandise::getTag,tag);
        }
        if (externaldisplaypicture != null){
            updateWrapper.lambda().set(Merchandise::getExternaldisplaypicture,externaldisplaypicture);
        }
        if (displaypictures != null){
            updateWrapper.lambda().set(Merchandise::getDisplaypictures,displaypictures);
        }
        if (price != null){
            updateWrapper.lambda().set(Merchandise::getPrice,price);
        }
        if (briefintroduction != null){
            updateWrapper.lambda().set(Merchandise::getBriefintroduction,briefintroduction);
        }

        updateWrapper.lambda().eq(Merchandise::getId,id);//判断依据
        merchandiseMapper.update(null,updateWrapper);
        return ResultUtils.success(SUCCESS);
    }
}

