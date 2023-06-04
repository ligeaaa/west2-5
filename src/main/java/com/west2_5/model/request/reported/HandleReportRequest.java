package com.west2_5.model.request.reported;

import lombok.Data;

@Data
public class HandleReportRequest {

    //举报Id
    private Long reportedId;

    //商品是否下架
    private Integer state;

}
