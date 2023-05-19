package com.west2_5.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
     * Endpoint 指 WebSocket 服务器端的入口点或终点，
     * 它是客户端与服务器之间建立 WebSocket 连接的地址。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")  // 定义端点
                .setAllowedOrigins("http://localhost:8071") //允许跨域
                .addInterceptors(new WebSocketInterceptor())
                .withSockJS();         // 启用SockJS支持


        /*
         * SockJS 使用底层的传输协议（如 HTTP）进行通信（请求地址允许以 HTTP 开头；否则以ws开头）
         */
    }

    /*
    * Message Broker 是一种中间件组件(服务)，用于处理和路由消息的传递。
    * 它充当消息传递系统中的中间层，负责接收来自发送者的消息，并将其路由到相应的接收者。
    */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        /*
         * 如果有一个客户端发送消息到 "/app/hello"，
         * 那么 Spring WebSocket 将会查找带有 @MessageMapping("/hello") 注解的方法来处理该消息
         */
        config.setApplicationDestinationPrefixes("/app"); // 定义应用程序端点前缀


        /*
        * （定义消息代理前缀）
        * Spring WebSocket 提供的一种基于内存的消息代理实现
        * 如果有一个消息发送到了 "/topic/greetings"，
        * 那么消息代理将会将该消息广播给所有订阅了 "/topic/greetings" 的客户端
        */
        config.enableSimpleBroker("/topic"); // 主题是一种发布-订阅（publish-subscribe）通信模式
        config.enableSimpleBroker("/queue"); // 表示消息代理中的队列(点对点通信模式)

        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user
        config.setUserDestinationPrefix("/user");

    }



}
