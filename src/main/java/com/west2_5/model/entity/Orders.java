package com.west2_5.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单(Orders)表实体类
 *
 * @author makejava
 * @since 2023-06-04 00:58:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Orders {
    //订单id
    @TableId
    private Long orderId;

    private Long merchandiseId;
    //售卖者id
    private Long sellerId;
    //购买者id
    private Long buyerId;
    //实际成交价格
    private Double price;
    //订单状态（0表示待确认，1表示发货，2表示收货）
    private Integer state;
    
    private Date createTime;
    
    private Date updateTime;



}
