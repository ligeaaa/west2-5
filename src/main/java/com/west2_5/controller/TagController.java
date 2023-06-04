package com.west2_5.controller;

import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Tag;
import com.west2_5.model.request.tag.AddTagRequest;
import com.west2_5.service.TagService;
import com.west2_5.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 游戏类型(Tag)表控制层
 *
 * @author makejava
 * @since 2023-06-02 10:32:03
 */
@RestController
@RequestMapping("tag")
public class TagController {

    @Resource
    private TagService tagService;

    @Resource
    private UserService userService;

    /**
     * 添加标签
     *
     * @author Lige
     * @since 2023-06-02
     */
    @PostMapping("/admin/add")
    private ResponseResult addTag(@RequestBody AddTagRequest addTagRequest) {
        userService.checkAdmin();
        return tagService.addTag(addTagRequest);
    }

    /**
     * 停用标签
     *
     * @author Lige
     * @since 2023-06-02
     */
    @PostMapping("/admin/stopTag")
    private ResponseResult stopTag(@RequestParam Long tagId) {
        userService.checkAdmin();
        return tagService.stopTag(tagId);
    }

    /**
     * 启用标签
     *
     * @author Lige
     * @since 2023-06-02
     */
    @PostMapping("/admin/startTag")
    private ResponseResult startTag(@RequestParam Long tagId) {
        userService.checkAdmin();
        return tagService.startTag(tagId);
    }

    /**
     * 展示可选游戏标签
     *
     * @author Lige
     * @since 2023-06-02
     */
    @GetMapping("/publish/getTags")
    private ResponseResult<List<Tag>> getTags() {
        return tagService.getTags();
    }


}

