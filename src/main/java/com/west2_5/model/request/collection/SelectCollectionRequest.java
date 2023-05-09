package com.west2_5.model.request.collection;



import com.west2_5.common.PageRequest;
import lombok.Data;



@Data
public class SelectCollectionRequest extends PageRequest {
    private static final long serialVersionUID = 3191241716373120793L;

    private Long userId;

    private Long merchandiseId;

    private String alias;

    private String groupName;

}
