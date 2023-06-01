package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.Message;


/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2023-06-01 22:53:22
 */
public interface MessageService extends IService<Message> {

    ResponseResult getMessageList(Long sessionId);
}
