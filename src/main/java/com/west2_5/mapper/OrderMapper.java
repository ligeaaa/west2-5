package com.west2_5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.west2_5.model.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
