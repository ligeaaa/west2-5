package com.west2_5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.mapper.SessionListMapper;
import com.west2_5.model.entity.SessionList;
import com.west2_5.model.entity.User;
import com.west2_5.service.FavoritesService;
import com.west2_5.service.SessionListService;
import com.west2_5.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;

/**
 * (SessionList)表服务实现类
 *
 * @author makejava
 * @since 2023-06-01 22:53:22
 */
@Service("sessionService")
public class SessionListServiceImpl extends ServiceImpl<SessionListMapper, SessionList> implements SessionListService {

    @Resource
    private UserService userService;

    /**
     * 增加unReadCount
     * @author Lige
     * @since 2023-06-02
     */
    @Override
    public void addUnReadCount(Long toUserId, Long fromUserId) {
        //找到对应的sessionId
        SessionList sessionList = selectIdByUser(toUserId, fromUserId);

        //使unReadCount+1
        sessionList.setUnReadCount(sessionList.getUnReadCount() + 1);
        saveOrUpdate(sessionList);
        return;
    }

    /**
     * 根据发送者Id和被发送者Id查询session
     * @author Lige
     * @since 2023-06-02
     */
    @Override
    public SessionList selectIdByUser(Long toUserId, Long userId) {
        //找到对应的sessionId
        LambdaQueryWrapper<SessionList> lambdaQueryWrapper = new LambdaQueryWrapper<SessionList>();
        lambdaQueryWrapper.eq(SessionList::getToUserId, toUserId);
        lambdaQueryWrapper.eq(SessionList::getUserId, userId);
        return this.getOne(lambdaQueryWrapper);
    }

    /**
     * 清零unReadCount
     * @author Lige
     * @since 2023-06-02
     */
    @Override
    public void delUnReadCount(Long toUserId, Long fromUserId) {
        //找到对应的sessionId
        SessionList sessionList = selectIdByUser(toUserId, fromUserId);

        //清零unReadCount
        sessionList.setUnReadCount(0);
        updateById(sessionList);
        return;
    }

    /**
     * 创建会话
     * @author Lige
     * @since 2023-06-02
     */
    @Override
    public ResponseResult createSession(Long toUserId) {

        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            throw new BusinessException(NULL_ERROR);
        }

        //建立双方的会话
        createSessionByUsers(user, toUserId);
        createSessionByUsers(userService.getByUserId(toUserId), user.getUserId());

        return ResponseResult.success();
    }

    /**
     * 查找当前用户已建立的会话
     * @author Lige
     * @since 2023-06-02
     */
    @Override
    public List<SessionList> getByUserId() {
        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            throw new BusinessException(NULL_ERROR);
        }

        //设置查询条件
        LambdaQueryWrapper<SessionList> lambdaQueryWrapper = new LambdaQueryWrapper<SessionList>();
        lambdaQueryWrapper.eq(SessionList::getUserId, user.getUserId());

        return list(lambdaQueryWrapper);
    }

    /**
     * 通过userId和ToUserId来建立会话包装类
     * @author Lige
     * @since 2023-06-02
     */
    public void createSessionByUsers(User user, Long toUserId) {
        SessionList sessionList = new SessionList();
        sessionList.setUserId(user.getUserId());
        sessionList.setUnReadCount(0);
        sessionList.setListName(user.getUserName());
        sessionList.setToUserId(toUserId);
        save(sessionList);
    }
}

