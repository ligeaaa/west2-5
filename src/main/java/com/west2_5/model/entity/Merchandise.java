package com.west2_5.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 游戏账号商品
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("merchandise")
public class Merchandise  {

    private Long merchandiseId;

    private Long sellerId;

    private Long tagId;

    private String title;

    private Double price;

    private String introduction;

    private String pictures;

    /**
     *  1：上架状态【默认】
     *  2：已下架
     */
    private Integer status;

}
