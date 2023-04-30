package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.BaseResponse;
import com.west2_5.model.entity.Merchandise;


/**
 * (Merchandise)表服务接口
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
public interface MerchandiseService extends IService<Merchandise> {

    BaseResponse addMerchandise(Long userid, String title, String tag, String externaldisplaypicture, String displaypictures, Integer price, String briefintroduction);

    BaseResponse updateMerchandiseById(Long id, String title, String tag, String externaldisplaypicture, String displaypictures, Integer price, String briefintroduction);
}
