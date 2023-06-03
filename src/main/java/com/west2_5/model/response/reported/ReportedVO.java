package com.west2_5.model.response.reported;

import com.baomidou.mybatisplus.annotation.TableId;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportedVO {

    private Long reportedId;
    //举报者备注原因
    private String reportedReason;
    //举报者Id
    private Long reporterId;
    //举报者姓名
    private String reporterName;
    //举报日期
    private LocalDateTime createTime;

    //商品
    private MerchandiseDetails merchandiseDetails;

    //用户


}
