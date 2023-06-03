package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Reported;
import com.west2_5.model.request.reported.AddReportRequest;
import com.west2_5.model.request.reported.HandleReportRequest;
import com.west2_5.model.response.reported.ReportedVO;

import java.util.List;


/**
 * 举报信息(Reported)表服务接口
 *
 * @author makejava
 * @since 2023-06-03 22:10:07
 */
public interface ReportedService extends IService<Reported> {

    ResponseResult addReport(AddReportRequest reportRequest);


    ResponseResult handleReport(HandleReportRequest handleReportRequest);

    ResponseResult<List<ReportedVO>> getReport(PageRequest pageRequest);
}
