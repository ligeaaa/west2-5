package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.PageRequest;
import com.west2_5.common.ResponseResult;
import com.west2_5.mapper.ReportedMapper;
import com.west2_5.model.entity.Favorites;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.Reported;
import com.west2_5.model.entity.User;
import com.west2_5.model.request.reported.AddReportRequest;
import com.west2_5.model.request.reported.HandleReportRequest;
import com.west2_5.model.response.reported.ReportedVO;
import com.west2_5.service.MerchandiseService;
import com.west2_5.service.ReportedService;
import com.west2_5.service.UserService;

import org.apache.shiro.SecurityUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.west2_5.constants.ReportedConstant.HANDLE;
import static com.west2_5.constants.ReportedConstant.NOT_HANDLE;

/**
 * 举报信息(Reported)表服务实现类
 *
 * @author makejava
 * @since 2023-06-03 22:10:08
 */
@Service("reportedService")
public class ReportedServiceImpl extends ServiceImpl<ReportedMapper, Reported> implements ReportedService {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private UserService userService;

    /**
     * 举报商品
     * @author Lige
     * @since 2023-06-03
     */
    @Override
    public ResponseResult addReport(AddReportRequest reportRequest) {
        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        Reported reported = new Reported();
        reported.setMerchandiseId(reportRequest.getMerchandiseId());
        reported.setReporterId(userId);
        reported.setReportedReason(reportRequest.getReportedReason());
        reported.setState(NOT_HANDLE);
        save(reported);
        return ResponseResult.success();
    }

    /**
     * 处理举报
     * @author Lige
     * @since 2023-06-03
     */
    @Override
    public ResponseResult handleReport(HandleReportRequest handleReportRequest) {
        //判断是否为管理员
        userService.checkAdmin();

        //修改为已处理状态
        Long reportedId = handleReportRequest.getReportedId();
        Reported reported = getByReportedId(reportedId);
        reported.setState(HANDLE);
        updateById(reported);

        //处理商品状态
        Merchandise merchandise = merchandiseService.getByMerchandiseId(reported.getMerchandiseId());
        merchandise.setState(handleReportRequest.getState());
        merchandiseService.updateById(merchandise);
        return ResponseResult.success();
    }

    /**
     * 查看举报
     * @author Lige
     * @since 2023-06-03
     */
    @Override
    public ResponseResult<List<ReportedVO>> getReport(PageRequest pageRequest) {
        //判断是否为管理员
        userService.checkAdmin();

        //获取所有未处理的举报
        LambdaQueryWrapper<Reported> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reported::getState,NOT_HANDLE);
        Page<Reported> reportedPage =
                page(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()), lambdaQueryWrapper);
        List<Reported> reportedList = reportedPage.getRecords();

        //整理成VO
        List<ReportedVO> reportedVOS = new ArrayList<>();
        for (Reported reported : reportedList){
            ReportedVO reportedVO = new ReportedVO();
            BeanUtils.copyProperties(reported, reportedVO);
            reportedVO.setReporterName(userService.getByUserId(reported.getReporterId()).getUserName());
            reportedVO.setMerchandiseDetails(merchandiseService.getMerchandiseDetails(reported.getMerchandiseId()));
            reportedVOS.add(reportedVO);
        }

        return ResponseResult.success(reportedVOS);
    }


    public Reported getByReportedId(Long reportedId) {
        LambdaQueryWrapper<Reported> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reported::getReportedId, reportedId);
        return getOne(lambdaQueryWrapper);
    }
}

