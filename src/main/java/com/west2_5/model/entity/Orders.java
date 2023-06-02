package com.west2_5.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2023/5/31
 * @Author: RuiLin
 * @Description: 商品订单
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Orders {

    @TableId
    Long orderId;

    Long merchandiseId;
    Long sellerId;
    Long buyerId;
    double orderPrice;

   /**
    * 【简化一点流程】
    *  0：买家已付款
    *  1：卖家已发货
    *  2. 订单退款中
    *  3：订单已完成
    */
    int orderStatus;
}
