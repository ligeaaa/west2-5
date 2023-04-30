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
 * @since 2023-04-30 02:24:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message  {
    @TableId
    private Long id;

    //0表示系统消息，1表示用户消息，2表示评论，3表示反馈
    private String type;
    //内容
    private String content;
    //发送者id，0为系统
    private Long senduserid;
    //接收者id，0为系统
    private Long receuveuserid;
    
    private Date createTime;
    
    private Date updateTime;
    //0表示未读，1表示已读，2表示未审核
    private String statu;



}
