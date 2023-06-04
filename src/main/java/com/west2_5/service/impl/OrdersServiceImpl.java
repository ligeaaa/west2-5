package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseResult;
import com.west2_5.mapper.OrdersMapper;
import com.west2_5.model.entity.Merchandise;
import com.west2_5.model.entity.Orders;
import com.west2_5.model.entity.User;
import com.west2_5.model.response.orders.OrdersOverview;
import com.west2_5.model.response.user.UserBasicInfo;
import com.west2_5.service.MerchandiseService;
import com.west2_5.service.OrdersService;
import com.west2_5.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.west2_5.constants.OrdersConstant.*;

/**
 * 订单(Orders)表服务实现类
 *
 * @author makejava
 * @since 2023-06-04 00:58:42
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private UserService userService;

    /**
     * 下单
     * @author Lige
     * @since 2023-06-04
     */
    @Override
    public ResponseResult addOrder(Long merchandiseId) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        // 下架商品
        Merchandise merchandise = merchandiseService.outMerchandise(merchandiseId);

        Orders orders = new Orders();
        orders.setMerchandiseId(merchandiseId);
        orders.setSellerId(merchandise.getSellerId());
        orders.setBuyerId(userId);
        orders.setPrice(merchandise.getPrice());
        orders.setState(ORDER);

        save(orders);

        return ResponseResult.success();
    }

    /**
     * 确认收货
     * @author Lige
     * @since 2023-06-04
     */
    @Override
    public ResponseResult deliver(Long orderId) {
        Orders orders = getByOrdersId(orderId);
        orders.setState(DELIVER);
        updateById(orders);
        return ResponseResult.success();
    }

    /**
     * 确认收货
     * @author Lige
     * @since 2023-06-04
     */
    @Override
    public ResponseResult takeDeliver(Long orderId) {
        Orders orders = getByOrdersId(orderId);
        orders.setState(TAKE_DELIVER);
        updateById(orders);
        return ResponseResult.success();
    }

    /**
     * 买方查看订单
     * @author Lige
     * @since 2023-06-04
     */
    @Override
    public ResponseResult<List<OrdersOverview>> buyerGetOrders(Integer status) {

        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getBuyerId,userId);
        //状态判定
        if (status != null){
            lambdaQueryWrapper.eq(Orders::getState,status);
        }
        List<Orders> ordersList = list(lambdaQueryWrapper);

        List<OrdersOverview> ordersOverviewList = new ArrayList<>();

        for (Orders order: ordersList){
            OrdersOverview ordervo = new OrdersOverview();
            //添加订单信息
            BeanUtils.copyProperties(order,ordervo);

            ordervo.setMerchandiseDetails(merchandiseService.getMerchandiseOverview(order.getMerchandiseId()));
            //添加卖方信息
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            User seller = userService.getByUserId(order.getSellerId());
            userBasicInfo.setAvatar(seller.getAvatar());
            userBasicInfo.setUserName(seller.getUserName());
            userBasicInfo.setUserId(seller.getUserId());
            ordervo.setUserBasicInfo(userBasicInfo);

            ordersOverviewList.add(ordervo);
        }

        return ResponseResult.success(ordersOverviewList);
    }

    /**
     * 卖方查看订单
     * @author Lige
     * @since 2023-06-04
     */
    @Override
    public ResponseResult<List<OrdersOverview>> sellerGetOrders(Integer status) {

        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getUserId();

        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getSellerId,userId);
        //状态判定
        if (status != null){
            lambdaQueryWrapper.eq(Orders::getState,status);
        }
        List<Orders> ordersList = list(lambdaQueryWrapper);

        List<OrdersOverview> ordersOverviewList = new ArrayList<>();

        for (Orders order: ordersList){
            OrdersOverview ordervo = new OrdersOverview();
            //添加订单信息
            BeanUtils.copyProperties(order,ordervo);
            ordervo.setState(getSellerState(order.getState()));
            //添加商品信息
            ordervo.setMerchandiseDetails(merchandiseService.getMerchandiseDetails(order.getMerchandiseId()));
            //添加买方信息
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            User seller = userService.getByUserId(order.getBuyerId());
            userBasicInfo.setAvatar(seller.getAvatar());
            userBasicInfo.setUserName(seller.getUserName());
            userBasicInfo.setUserId(seller.getUserId());
            ordervo.setUserBasicInfo(userBasicInfo);

            ordersOverviewList.add(ordervo);
        }

        return ResponseResult.success(ordersOverviewList);
    }

    /**
     * 根据orderId获取order
     * @author Lige
     * @since 2023-06-04
     */
    private Orders getByOrdersId(Long orderId){
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getOrderId,orderId);
        return getOne(lambdaQueryWrapper);
    }

    /**
     * 根据status返回对应字段(买方)
     * @author Lige
     * @since 2023-06-04
     */
    private String getBuyerState(Integer status){
        if (status == null){
            return "";
        }
        if (status == 0){
            return "我已付款";
        }
        if (status == 1){
            return "卖家已发货";
        }
        if (status == 2){
            return "交易成功";
        }
        return "";
    }

    /**
     * 根据status返回对应字段(卖方)
     * @author Lige
     * @since 2023-06-04
     */
    private String getSellerState(Integer status){
        if (status == null){
            return "";
        }
        if (status == 0){
            return "买家已付款";
        }
        if (status == 1){
            return "我已发货";
        }
        if (status == 2){
            return "交易成功";
        }
        return "";
    }

}

