package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.mapper.OrderMapper;
import com.west2_5.model.entity.Orders;
import com.west2_5.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void generateOrder(Orders orders) {

        orderMapper.insert(orders);
    }
}
