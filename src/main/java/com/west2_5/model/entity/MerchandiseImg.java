package com.west2_5.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("merchandise_img")
public class MerchandiseImg {

    Long imgId;
    Long merchandiseId;
    String imgUrl;
    int imgPriority;

}
