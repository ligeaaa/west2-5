package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;

import java.util.List;

public interface MerchandiseService extends IService<Merchandise> {

    void addMerchandise(AddMerchandiseRequest merchandise);

    MerchandiseDetails getMerchandiseDetails(Long merchandiseId);

    List<MerchandiseOverview> overviewMerchandise(int currentPage);


}
