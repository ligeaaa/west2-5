package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;

public interface MerchandiseService extends IService<Merchandise> {

    void addMerchandise(AddMerchandiseRequest merchandise);

}
