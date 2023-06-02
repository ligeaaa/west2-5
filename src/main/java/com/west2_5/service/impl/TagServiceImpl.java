package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Tag;
import com.west2_5.mapper.TagMapper;
import com.west2_5.model.request.tag.AddTagRequest;
import com.west2_5.service.TagService;
import com.west2_5.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.west2_5.constants.TagConstant.START_TAG;
import static com.west2_5.constants.TagConstant.STOP_TAG;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult addTag(AddTagRequest addTagRequest) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(addTagRequest, tag);
        save(tag);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult stopTag(Long tagId) {
        Tag tag = getByTagId(tagId);
        tag.setStatus(STOP_TAG);
        updateById(tag);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult startTag(Long tagId) {
        Tag tag = getByTagId(tagId);
        tag.setStatus(START_TAG);
        updateById(tag);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<List<Tag>> getTags() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getStatus,START_TAG);
        return ResponseResult.success(list(wrapper));
    }




    public Tag getByTagId(Long tagId){
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getTagId,tagId);
        return getOne(wrapper);
    }

}

