package com.sms.util;

import com.sms.entity.Account;
import javax.servlet.http.HttpSession;

/**
 * 登录鉴权工具类
 */
public class AuthUtil {

    private static final String SESSION_USER = "currentUser";

    /**
     * 将用户信息存入Session
     */
    public static void setLoginUser(HttpSession session, Account account) {
        session.setAttribute(SESSION_USER, account);
        session.setAttribute("userId", account.getUserId());
        session.setAttribute("username", account.getUsername());
        session.setAttribute("roleType", account.getRoleType());
    }

    /**
     * 获取当前登录用户
     */
    public static Account getLoginUser(HttpSession session) {
        return (Account) session.getAttribute(SESSION_USER);
    }

    /**
     * 检查是否已登录
     */
    public static boolean isLogin(HttpSession session) {
        return getLoginUser(session) != null;
    }

    /**
     * 获取当前用户角色
     */
    public static String getRole(HttpSession session) {
        Account account = getLoginUser(session);
        return account != null ? account.getRoleType() : null;
    }

    /**
     * 清除登录状态
     */
    public static void clearLogin(HttpSession session) {
        session.removeAttribute(SESSION_USER);
        session.removeAttribute("userId");
        session.removeAttribute("username");
        session.removeAttribute("roleType");
        session.invalidate();
    }
}