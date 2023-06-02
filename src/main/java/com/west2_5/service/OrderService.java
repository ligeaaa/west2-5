package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.model.entity.Orders;

public interface OrderService extends IService<Orders> {

    void generateOrder(Orders orders);
}
