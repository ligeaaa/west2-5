package com.west2_5.model.response.merchandise;

import lombok.Data;

@Data
public class MerchandiseOverview {

    private Long merchandiseId;

    private String state;

    private String title;

    private Double price;

    private String picture; //第一张图片（封面）

}
