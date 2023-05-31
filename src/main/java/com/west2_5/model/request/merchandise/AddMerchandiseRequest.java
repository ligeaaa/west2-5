package com.west2_5.model.request.merchandise;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddMerchandiseRequest {

    private Long sellerId;

    private Long tagId;

    private String title;

    private Double price;

    private String introduction;

    private List<String> pictures;

}
