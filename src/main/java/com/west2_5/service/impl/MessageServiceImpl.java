package com.west2_5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.mapper.MessageMapper;
import com.west2_5.model.entity.Message;
import org.springframework.stereotype.Service;
import com.west2_5.service.MessageService;

/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 02:24:06
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}

