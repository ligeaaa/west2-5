package com.west2_5.model.response.favorites;

import com.west2_5.model.response.merchandise.MerchandiseDetails;
import com.west2_5.model.response.merchandise.MerchandiseOverview;
import com.west2_5.model.response.user.UserBasicInfo;
import lombok.Data;

@Data
public class FavoritesDetails {

    private Long favoriteId; //

    private Long userId; //

    private Long merchandiseId; //

    private MerchandiseDetails merchandiseDetails;

    private UserBasicInfo userBasicInfo;
}
