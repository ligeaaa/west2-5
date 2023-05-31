package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.BaseResponse;
import com.west2_5.model.entity.Merchandise;

public interface MerchandiseService extends IService<Merchandise> {

    BaseResponse addMerchandise(Long userid, String title, String tag, String pictures, double price, String introduction);

}
