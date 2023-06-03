package com.west2_5.model.response.orders;

import com.baomidou.mybatisplus.annotation.TableId;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.user.UserBasicInfo;
import lombok.Data;

import java.util.Date;

@Data
public class OrdersVO {

    //实际成交价格
    private Double price;
    //订单状态（0表示待确认，1表示发货，2表示收货）
    private String state;

    //商品id
    private Long merchandiseId;

    private MerchandiseDetails merchandiseDetails;

    //售卖者id
    private Long sellerId;
    //购买者id
    private Long buyerId;

    private UserBasicInfo userBasicInfo;
}
