package com.west2_5.model.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Message)表实体类
 *
 * @author makejava
 * @since 2023-06-01 22:53:22
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message  {
    @TableId
    private Long id;

    //消息发送者id
    private Long fromUserId;
    //消息发送者名称
    private String fromUserName;
    //消息接收者id
    private Long toUserId;
    //消息接收者名称
    private String toUserName;
    //消息内容
    private String content;
    //创建时间
    private Date createTime;
    //是否已读（0未读，1 已读）
    private Integer unreadFlag;



}
