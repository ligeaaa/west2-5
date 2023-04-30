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
 * (Merchandise)表实体类
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
    //商品id@TableId
    private Long id;

    //发布者id
    private Long userid;
    //商品名称
    private String title;
    //所属tag，中间用英文逗号隔开
    private String tag;
    //外部展示图片，默认为内部展示图片的第一张
    private String externaldisplaypicture;
    //内部展示的所有图片
    private String displaypictures;
    //价格
    private Integer price;
    //简介
    private String briefintroduction;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    //状态（1代表已删除，2表示未审核，3表示审核通过，4表示已交易成功）
    private Integer statu;



}
