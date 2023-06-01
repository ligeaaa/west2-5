package com.west2_5.model.response.merchandise;

import com.west2_5.common.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class MerchandiseDetails{

    private Long merchandiseId;

    private String title;

    private String tagName;

    private Double price;

    private String introduction;

    private List<String> pictures;

    //卖家信息
    private Long userId;

    private String userName;

    private String avatar;

}
