package com.west2_5.controller;



import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.request.reported.AddReportRequest;
import com.west2_5.model.request.reported.HandleReportRequest;
import com.west2_5.model.response.reported.ReportedVO;
import com.west2_5.service.ReportedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 举报信息(Reported)表控制层
 *
 * @author makejava
 * @since 2023-06-03 22:10:05
 */
@RestController
@RequestMapping("reported")
public class ReportedController {

    @Autowired
    private ReportedService reportedService;

    /**
     * 举报商品
     * @author Lige
     * @since 2023-06-03
     */
    @PostMapping("/user/report")
    private ResponseResult addReport(@RequestBody AddReportRequest reportRequest){
        return reportedService.addReport(reportRequest);
    }

    /**
     * 处理举报
     * @author Lige
     * @since 2023-06-03
     */
    @PostMapping("/admin/handle")
    private ResponseResult handleReport(@RequestBody HandleReportRequest handleReportRequest){
        return reportedService.handleReport(handleReportRequest);
    }

    /**
     * 查看举报
     * @author Lige
     * @since 2023-06-03
     */
    @GetMapping("/admin/getReport")
    private ResponseResult<List<ReportedVO>> getReport(@RequestBody PageRequest pageRequest){
        return reportedService.getReport(pageRequest);
    }

}

