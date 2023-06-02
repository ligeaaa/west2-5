package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Tag;
import com.west2_5.model.request.tag.AddTagRequest;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
public interface TagService extends IService<Tag> {

    ResponseResult addTag(AddTagRequest addTagRequest);

    ResponseResult stopTag(Long tagId);

    ResponseResult startTag(Long tagId);

    ResponseResult<List<Tag>> getTags();
}
