package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Orders;
import org.apache.ibatis.annotations.Mapper;


/**
 * 订单(Orders)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-04 00:58:20
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}
