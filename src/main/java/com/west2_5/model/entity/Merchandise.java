package com.west2_5.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("merchandise")
public class Merchandise  {

    @TableId
    private Long merchandiseId;

    private Long sellerId;

    private Long tagId;

    private String title;

    private Double price;

    private String introduction;

    /**
     * 按理还要建一个merchandise_state表（state_id / state_name）
     * 但摸鱼从简（手动返回文字，但数据库还是数字格式）
     *  0：已上架
     *  1：已下架
     */
    private Integer state;

}
