package com.west2_5.constants;

import lombok.Data;

@Data
public class QueryPageParam {
    //默认
    private static Long PAGE_SIZE=20l;
    private static Long PAGE_NUM=1l;

    private Long pageSize=PAGE_SIZE;
    private Long pageNum=PAGE_NUM;
}
