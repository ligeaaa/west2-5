package com.west2_5.controller;

import com.west2_5.common.ResponseResult;
import com.west2_5.model.response.orders.OrdersOverview;
import com.west2_5.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单(Orders)表控制层
 *
 * @author makejava
 * @since 2023-06-04 00:58:20
 */
@RestController
@RequestMapping("orders")
public class OrdersController{

    @Autowired
    private OrdersService ordersService;

    /**
     * 下单
     * @author Lige
     * @since 2023-06-04
     */
    @PostMapping("/user/addOrder")
    private ResponseResult addOrder(@RequestBody Long merchandiseId){
        return ordersService.addOrder(merchandiseId);
    }

    /**
     * 发货
     * @author Lige
     * @since 2023-06-04
     */
    @PostMapping("/user/deliver")
    private ResponseResult deliver(@RequestBody Long orderId){
        return ordersService.deliver(orderId);
    }

    /**
     * 确认收货
     * @author Lige
     * @since 2023-06-04
     */
    @PostMapping("/user/takeDeliver")
    private ResponseResult takeDeliver(@RequestBody Long orderId){
        return ordersService.takeDeliver(orderId);
    }

    /**
     * 买方查看订单
     * @author Lige
     * @since 2023-06-04
     */
    @PostMapping("/user/buyerGetOrders")
    private ResponseResult<List<OrdersOverview>> buyerGetOrders(@RequestBody Integer status){
        return ordersService.buyerGetOrders(status);
    }

    /**
     * 卖方查看订单
     * @author Lige
     * @since 2023-06-04
     */
    @PostMapping("/user/sellerGetOrders")
    private ResponseResult<List<OrdersOverview>> sellerGetOrders(@RequestBody Integer status){
        return ordersService.sellerGetOrders(status);
    }


}

