package com.west2_5.controller;

import com.west2_5.common.ResponseResult;
import com.west2_5.exception.BusinessException;
import com.west2_5.model.entity.SessionList;
import com.west2_5.model.entity.User;
import com.west2_5.service.SessionListService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.west2_5.common.ResponseCode.NULL_ERROR;

/**
 * (SessionList)表控制层
 *
 * @author makejava
 * @since 2023-06-01 22:53:11
 */
@RestController
@RequestMapping("session")
public class SessionListController {

    @Autowired
    private SessionListService sessionListService;

    /**
     * 查找当前用户已建立的会话
     * @author Lige
     * @since 2023-06-02
     */
    @GetMapping("/user/sessionList/already")
    public ResponseResult<List<SessionList>> sessionListAlready(){
        return  ResponseResult.success(sessionListService.getByUserId());
    }

//    /**
//     * 可建立会话的用户
//     * @author Lige
//     * @since 2023-06-02
//     */
//    @GetMapping("/sessionList/not")
//    public ResponseResult sessionListNot(@RequestParam Long id){
//        return ResponseResult.success();
//    }

    /**
     * 创建会话
     * @author Lige
     * @since 2023-06-02
     */
    @PostMapping("/user/createSession")
    public ResponseResult createSession(@RequestParam Long toUserId){
        return sessionListService.createSession(toUserId);
    }

    /**
     * 删除会话
     * @author Lige
     * @since 2023-06-02
     */
    @DeleteMapping("/user/delSession")
    public ResponseResult delSession(@RequestParam Integer sessionId){
        sessionListService.removeById(sessionId);
        return ResponseResult.success();
    }


}

