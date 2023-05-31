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
 * 用户个人收藏的商品
 *
 * @author makejava
 * @since 2023-05-07 14:23:40
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("favorites")
public class Favorites  {
    @TableId
    private Long favoriteId;

    private Long userId;
    
    private Long merchandiseId;
}
