package com.west2_5.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**     shiro 是根据 sessionId 来识别是不是同一个 request
 *      前后端分离/跨域 session 很可能就变化
 *      (未分离，session 本身自动存入 cookie，不会变化)
 *      需要用自定义标记来表明是同一个请求
 *      登录成功，前端本地保存后端传来的 sessionId
 *      并使前端每次向后端发送请求的Header都会带上它
 *      后端再对这个 sessionId 解析，拿到正确的 session
 */

// TODO: 是否可以持久化保存session到redis/mysql，避免后台重启用户需要重新登陆

public class ShiroSession extends DefaultWebSessionManager {

    private static final String AUTH_TOKEN = "AuthToken";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public ShiroSession() {
        super();
        // shiro session 失效时间（默认 30 分钟），负数永不过期，直到用户手动退出（或服务器关闭）
        setGlobalSessionTimeout(-1);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

        // 获取请求头中的 AUTH_TOKEN 的值
        String sessionId = WebUtils.toHttp(request).getHeader(AUTH_TOKEN);

        if (!StringUtils.hasLength(sessionId) ){
            // 如果 token 为空，则按照父类的方式在 cookie 进行获取 sessionId
            return super.getSessionId(request, response);
        } else {
            // 请求头中如果有 authToken, 则其值为 sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
    }

}
