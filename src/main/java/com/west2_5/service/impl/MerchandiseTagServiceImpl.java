package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.model.entity.MerchandiseTag;
import com.west2_5.mapper.MerchandiseTagMapper;
import com.west2_5.service.MerchandiseTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(MerchandiseTag)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("merchandiseTagService")
public class MerchandiseTagServiceImpl extends ServiceImpl<MerchandiseTagMapper, MerchandiseTag> implements MerchandiseTagService {

}

