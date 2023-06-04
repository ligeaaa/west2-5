package com.west2_5.model.response.merchandise;

import com.sun.org.apache.xpath.internal.objects.XString;
import com.west2_5.common.PageRequest;
import com.west2_5.model.response.user.UserBasicInfo;
import lombok.Data;

import java.util.List;

@Data
public class MerchandiseDetails{

    private Long merchandiseId;

    private Integer state;

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
