package com.west2_5.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (SessionList)表实体类
 *
 * @author makejava
 * @since 2023-06-01 22:53:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("session_list")
public class SessionList {
    @TableId
    private Long id;

    //发送者id
    private Long userId;
    //被发送者id
    private Long toUserId;
    //会话名称
    private String listName;
    //未读消息数
    private Integer unReadCount;



}
