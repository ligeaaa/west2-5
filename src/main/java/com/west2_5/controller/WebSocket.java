package com.west2_5.controller;

import com.west2_5.mapper.MessageMapper;
import com.west2_5.mapper.SessionListMapper;
import com.west2_5.mapper.UserMapper;
import com.west2_5.model.entity.Message;
import com.west2_5.model.entity.SessionList;
import com.west2_5.model.entity.User;
import com.west2_5.service.MessageService;
import com.west2_5.service.SessionListService;
import com.west2_5.service.UserService;
import com.west2_5.utils.CurPool;
import com.west2_5.utils.JsonUtils;
import com.west2_5.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@Component
@ServerEndpoint("/websocket/{userId}/{sessionId}")
//此注解相当于设置访问URL
public class WebSocket {

    @Autowired
    private SessionListService sessionListService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    private Session session;

    /**
     * 前端调用连接的时候，就会调用这个方法。
     * @author Lige
     * @since 2023-06-01
     */
    @OnOpen
    public void onOpen(Session session,@PathParam(value="userId")Long userId, @PathParam(value="sessionId")String sessionId) {
        this.session = session;
        CurPool.webSockets.put(userId,this);

        //将Session存入sessionPool
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        CurPool.sessionPool.put(userId , list);

        System.out.println("【websocket消息】有新的连接，总数为:"+CurPool.webSockets.size());
    }

    /**
     * 当连接中断的时候，会调用这个方法。
     * @author Lige
     * @since 2023-06-01
     */
    @OnClose
    public void onClose() {
        // 断开连接删除用户删除session
        Integer userId = Integer.parseInt(this.session.getRequestParameterMap().get("userId").get(0));
        CurPool.sessionPool.remove(userId);
        CurPool.webSockets.remove(userId);
        User user = userService.getById(userId);
        CurPool.curUserPool.remove(user.getUserName());
        System.out.println("【websocket消息】连接断开，总数为:"+CurPool.webSockets.size());
    }

    /**
     * 当前端向我们发消息（this.websock.send(content)）的时候，就进入了这个方法
     * @author Lige
     * @since 2023-06-01
     */
    @OnMessage
    public void onMessage(String message) {
        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);
        if (sessionId == null){
            System.out.println("sessionId 错误");
        }
        //找到对应的session和User
        SessionList sessionList = sessionListService.getById(Long.parseLong(sessionId));
        User user = userService.getById(sessionList.getUserId());

        //将相关信息存入Message表
        Message tempMessage = new Message();
        tempMessage.setContent(message);
        tempMessage.setCreateTime(new Date());
        tempMessage.setFromUserId(sessionList.getUserId());
        tempMessage.setFromUserName(user.getUserName());
        tempMessage.setToUserId(sessionList.getToUserId());
        tempMessage.setToUserName(sessionList.getListName());
        tempMessage.setUnreadFlag(0);
        messageService.save(tempMessage);

        // 判断被发送的用户是否存在（不存在即不在线），不存在就结束
        List<Object> list = CurPool.sessionPool.get(sessionList.getToUserId());
        if (list == null || list.isEmpty()){
            // 用户不存在，更新未读数
            sessionListService.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
        }else{
            // 用户存在，判断会话是否存在
            String id = sessionListService.selectIdByUser(sessionList.getToUserId(), sessionList.getUserId()).getId().toString()+"";
            String o = list.get(0) + "";
            if (id.equals(o)){
                // 会话存在直接发送消息
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(tempMessage));
            }else {
                // 判断会话列表是否存在
                if (id == null || "".equals(id) || "null".equals(id)){
                    // 新增会话列表
                    SessionList tmpSessionList = new SessionList();
                    tmpSessionList.setUserId(sessionList.getToUserId());
                    tmpSessionList.setToUserId(sessionList.getUserId());
                    tmpSessionList.setListName(user.getUserName());
                    tmpSessionList.setUnReadCount(1);
                    sessionListService.save(tmpSessionList);
                    sessionListService.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
                }else {
                    // 更新未读消息数量
                    sessionListService.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
                }
                sendTextMessage(sessionList.getToUserId() , JsonUtils.objectToJson(tempMessage));
            }
        }
        System.out.println("【websocket消息】收到客户端消息:"+message);
    }

    /**
     * 发送文本消息
     * 通过 session.getBasicRemote().sendText(message) 这个方法主动的向前端推送消息。
     * @author Lige
     * @since 2023-06-01
     */
    // 此为单点消息 (发送文本)
    public void sendTextMessage(Long userId, String message) {
        Session session = (Session)CurPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}