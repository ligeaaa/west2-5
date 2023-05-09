package com.west2_5.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.west2_5.constants.SystemConstants.DATACENTER_ID;
import static com.west2_5.constants.SystemConstants.WORK_ID;

/**
 * <p>
 * 创建并维护Snowflake对象为单例
 * <p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-03-18
 **/

@Component
public class SnowflakeUtil {
    @Bean
    public Snowflake getSnowflake() {
        return IdUtil.getSnowflake(WORK_ID, DATACENTER_ID);
    }
}
