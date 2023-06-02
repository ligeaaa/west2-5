package com.west2_5.model.request.tag;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddTagRequest {

    private Long tagId;

    private String tagName;

}
