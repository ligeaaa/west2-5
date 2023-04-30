package com.west2_5.common;

import lombok.Data;

/**
 * 统一分页请求
 *
 * @author https://github.com/liyupi
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

}
