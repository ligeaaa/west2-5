package com.west2_5.model.request.merchandise;

import lombok.Data;

@Data
public class UpdateMerchandiseById {
    private static final long serialVersionUID = 3191241716373120793L;
    private Long id;
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
}
