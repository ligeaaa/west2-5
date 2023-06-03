package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.request.merchandise.AddMerchandiseRequest;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;

import java.util.List;

public interface MerchandiseService extends IService<Merchandise> {

    //查
    Merchandise getByMerchandiseId(Long merchandiseId);

    MerchandiseDetails getMerchandiseDetails(Long merchandiseId);

    List<MerchandiseOverview> overviewMerchandise(int currentPage);

    List<MerchandiseOverview> getMyPublishedMerchandise(Long userId, int currentPage);

    List<MerchandiseOverview> getMyOutMerchandise(Long userId, int currentPage);

    //增删改
    void addMerchandise(AddMerchandiseRequest merchandise);

    void outMerchandise(Long merchandiseId);


}
