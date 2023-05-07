package com.west2_5.model.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Favorites)表实体类
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
    private Long id;

    private Long userId;
    
    private Long merchandiseId;
    
    private String alias;
    
    private String groupName;
    
    private Date createTime;
    
    private Date updateTime;



}
