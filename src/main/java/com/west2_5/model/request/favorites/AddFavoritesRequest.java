package com.west2_5.model.request.favorites;

import lombok.Data;

import java.util.List;

/**
 * “添加收藏”请求体
 * @author Lige
 * @since 2023-06-01
 */
@Data
public class AddFavoritesRequest {

    /**
     * 商品Id
     */
    private Long merchandiseId;

}
