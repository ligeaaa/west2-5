package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.mapper.OrderMapper;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.Orders;
import com.west2_5.service.MerchandiseService;
import com.west2_5.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private MerchandiseService merchandiseService;

    //购买商品生成订单
    @Override
    public void createOrder(Long buyerId, Long merchandiseId) {
        Merchandise merchandise = merchandiseService.getByMerchandiseId(merchandiseId);
        merchandiseService.outMerchandise(merchandiseId); //下架商品

        Orders orders = new Orders();
        BeanUtils.copyProperties(merchandise, orders);
        orders.setOrderPrice(merchandise.getPrice());
        orders.setBuyerId(buyerId);
        orderMapper.insert(orders);
    }
}
