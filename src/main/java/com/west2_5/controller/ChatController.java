package com.west2_5.controller;

import com.west2_5.model.entity.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/*
 *客户端发送消息到/app/chat目的地，
 * （前端与 http://localhost:8090/chat 建立连接）
 *服务器端接收并处理这些消息
 */
@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    /*
     * 返回值并不会直接发送给客户端
     * 客户端需要通过订阅相应的目的地来接收来自服务器的消息
     */

    @MessageMapping("/hello")  //浏览器给后端发消息的地址
    @SendTo("/topic/greeting") //服务器有消息时，给浏览器的地址
    public String greeting(String username) {
        log.info(username);
        return "Hello, " + username;  //相当于是return给/topic/greeting
    }

    @MessageMapping("/chat/{recipient}")
    public void sendMessageToUser(ChatMessage chatMessage, @DestinationVariable("recipient") String recipient){
        //消息将被发送到 /user/{recipient}/queue/messages 目的地
        log.info(chatMessage.getContent());
        messagingTemplate.convertAndSendToUser(recipient, "/queue/messages", chatMessage);
    }

}



