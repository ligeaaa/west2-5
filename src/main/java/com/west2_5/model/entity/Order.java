package com.west2_5.model.entity;

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
public class Order {

    Long orderId;
    Long sellerId;
    Long buyerId;

   /**
    * 【简化一点流程】
    *  1：买家已付款
    *  2：卖家已发货
    *  3：订单已完成
    */
    int orderStatus;
}
