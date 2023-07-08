package com.west2_5.model.response.orders;

import com.west2_5.model.entity.Orders;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.user.UserBasicInfo;
import lombok.Data;

@Data
public class OrdersVO {

    private Long orderId;

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
