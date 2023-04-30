package com.west2_5.model.request.merchandise;

import com.west2_5.common.PageRequest;
import lombok.Data;

@Data
public class SelectMerchandiseRequest extends PageRequest {
    private static final long serialVersionUID = 3191241716373120793L;
    //发布者id
    private Long userid;
    //商品名称
    private String title;
    //所属tag，中间用英文逗号隔开
    private String tag;
    //价格
    private Integer price;
    //简介
    private String briefintroduction;
}
