package com.sms.config;

import com.sms.util.AuthUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 - 检查用户是否已登录
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 检查Session中是否有登录用户
        if (!AuthUtil.isLogin(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}