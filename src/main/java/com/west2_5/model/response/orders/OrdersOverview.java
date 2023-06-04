package com.west2_5.model.response.orders;

import com.west2_5.model.entity.Orders;
import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;
import com.west2_5.model.response.user.UserBasicInfo;
import lombok.Data;

@Data
public class OrdersOverview {

    private Orders orders;

    private MerchandiseOverview merchandiseOverview;

    private UserBasicInfo userBasicInfo;

}
