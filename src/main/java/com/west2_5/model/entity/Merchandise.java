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
     *  1：上架状态【默认】
     *  2：已下架
     */
    private Integer status;

}
