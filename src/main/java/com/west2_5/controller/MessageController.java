package com.west2_5.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.Message;
import com.west2_5.model.entity.SessionList;
import com.west2_5.service.FavoritesService;
import com.west2_5.service.MessageService;
import com.west2_5.service.SessionListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;

/**
 * (Message)表控制层
 *
 * @author makejava
 * @since 2023-06-01 22:53:22
 */
@RestController
@RequestMapping("message")
public class MessageController{

    @Resource
    private MessageService messageService;

    /**
     * 查看对应session的消息列表
     * @author Lige
     * @since 2023-06-02
     */
    @GetMapping("/user/List")
    public ResponseResult getMessageList(@RequestParam Long sessionId){
        return messageService.getMessageList(sessionId);
    }


}

