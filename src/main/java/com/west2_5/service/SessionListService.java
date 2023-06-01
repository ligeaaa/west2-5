package com.west2_5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.west2_5.common.ResponseResult;
import com.west2_5.model.entity.SessionList;

import java.util.List;


/**
 * (SessionList)表服务接口
 *
 * @author makejava
 * @since 2023-06-01 22:53:21
 */
public interface SessionListService extends IService<SessionList> {

    void addUnReadCount(Long toUserId, Long userId);

    SessionList selectIdByUser(Long toUserId, Long userId);

    void delUnReadCount(Long toUserId, Long fromUserId);

    ResponseResult createSession(Long toUserId);

    List<SessionList> getByUserId();
}
