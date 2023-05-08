package com.west2_5.model.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *  TODO
 * <p>
 *
 * @author Zaki
 * @version TODO
 * @since 2023-04-09
 **/
@Data
public class UpdateUserInfoRequest  implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户id
     */
    @NotNull(message = "userId不能为空")
    private Long userId;


    private String nickName;


    /**
     * 签名
     */
    private String signature;


    /**
     * 地址
     */
    private String address;


    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;


}
