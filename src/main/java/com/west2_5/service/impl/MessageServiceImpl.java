package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.mapper.MessageMapper;
import com.west2_5.model.entity.Message;
import com.west2_5.model.entity.SessionList;
import com.west2_5.service.MessageService;
import com.west2_5.service.SessionListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;

/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2023-06-01 22:53:22
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private SessionListService sessionListService;

    /**
     * 查看对应session的消息列表
     * @author Lige
     * @since 2023-06-02
     */
    @Override
    public ResponseResult getMessageList(Long sessionId) {
        SessionList sessionList = sessionListService.getById(sessionId);
        if(sessionList == null){
            throw new BusinessException(NULL_ERROR);
        }
        //获取发送者和被发送者Id
        Long fromUserId = sessionList.getUserId();
        Long toUserId = sessionList.getToUserId();

        //获取消息列表
        LambdaQueryWrapper<Message> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getFromUserId,fromUserId);
        lambdaQueryWrapper.eq(Message::getToUserId,toUserId);
        List<Message> messageList = list(lambdaQueryWrapper);

        // 更新消息已读
        messageRead(fromUserId, toUserId);
        // 更新会话里面的未读消息
        sessionListService.delUnReadCount(toUserId, fromUserId);
        return ResponseResult.success(messageList);
    }

    /**
     * 将对应发送者和被发送者的所有信息设置为已读
     * @author Lige
     * @since 2023-06-02
     */
    public void messageRead(Long fromUserId, Long toUserId) {
        LambdaQueryWrapper<Message> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getFromUserId,fromUserId);
        lambdaQueryWrapper.eq(Message::getToUserId,toUserId);
        List<Message> messageList = list(lambdaQueryWrapper);
        for (Message message : messageList) {
            message.setUnreadFlag(1);
            updateById(message);
        }
        return;
    }




}

