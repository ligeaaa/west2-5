package com.west2_5.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2023/5/31
 * @Author: RuiLin
 * @Description: 被举报商品
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reported {

    Long reportedId;
    Long reporterId; //举报者
    Long merchandiseId;

    /**
     *  1：虚假宣传
     *  2：有害信息
     *  3：其他
     */
    int reportedType;

    String reportedComment;

    boolean hasSettle;
}
