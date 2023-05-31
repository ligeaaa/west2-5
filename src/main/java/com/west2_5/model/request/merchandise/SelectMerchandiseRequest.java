package com.west2_5.model.request.merchandise;

import com.west2_5.common.PageRequest;
import lombok.Data;

@Data
public class SelectMerchandiseRequest extends PageRequest {
    private static final long serialVersionUID = 3191241716373120793L;

    private Long userid;

    private String title;

    private String tag;

    private Double price;

    private String picture;

    private String introduction;

}
