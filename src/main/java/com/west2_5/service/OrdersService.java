package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Orders;
import com.west2_5.model.response.orders.OrdersVO;

import java.util.List;


/**
 * 订单(Orders)表服务接口
 *
 * @author makejava
 * @since 2023-06-04 00:58:42
 */
public interface OrdersService extends IService<Orders> {

    ResponseResult addOrder(Long merchandiseId);

    ResponseResult deliver(Long orderId);

    ResponseResult takeDeliver(Long orderId);

    ResponseResult<List<OrdersVO>> buyerGetOrders(Integer status);

    ResponseResult<List<OrdersVO>> sellerGetOrders(Integer status);
}
