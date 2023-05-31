package com.west2_5.config.shiro;

import com.alibaba.fastjson2.JSONObject;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ShiroCustomizeFilter extends UserFilter {

    // 修改Shiro默认未登录重定向页面，返回json
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject res = new JSONObject();
        res.put("code", "-1");
        res.put("message", "登录已失效");
        response.getWriter().print(res.toString());
    }
}
