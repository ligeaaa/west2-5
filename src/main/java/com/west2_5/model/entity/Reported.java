package com.west2_5.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 举报信息(Reported)表实体类
 *
 * @author makejava
 * @since 2023-06-03 22:10:07
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("reported")
public class Reported {
    @TableId
    private Long reportedId;

    //商品Id
    private Long merchandiseId;
    //举报者Id
    private Long reporterId;
    //举报者备注原因
    private String reportedReason;
    //后台是否已处理
    private Integer state;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;



}
