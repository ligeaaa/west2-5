package com.west2_5.model.request.reported;

import lombok.Data;

import static com.west2_5.constants.ReportedConstant.HANDLE;

@Data
public class AddReportRequest {

    //商品Id
    private Long merchandiseId;
    //举报者备注原因
    private String reportedReason;

}
