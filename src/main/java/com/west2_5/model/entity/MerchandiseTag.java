package com.west2_5.model.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(MerchandiseTag)表实体类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("merchandise_tag")
public class MerchandiseTag  {
    //文章id@TableId
    private Long merchandiseId;
    //标签id@TableId
    private Long tagId;




}
