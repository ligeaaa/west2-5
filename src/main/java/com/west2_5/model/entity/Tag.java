package com.west2_5.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tag")
public class Tag  {

    @TableId
    private Long tagId;

    private String tagName;

    private Integer status;

}
