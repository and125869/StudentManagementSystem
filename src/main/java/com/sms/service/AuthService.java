package com.sms.service;

import com.sms.dao.AccountDAO;
import com.sms.entity.Account;
import com.sms.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 认证服务 - 处理登录、登出、密码修改等
 */
@Service
@Transactional
public class AuthService {

    @Autowired
    private AccountDAO accountDAO;

    @Value("${app.login.max-attempts:5}")
    private int maxAttempts;

    @Value("${app.login.lock-duration:1800}")
    private int lockDuration;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 明文密码
     * @return 登录结果
     */
    public LoginResult login(String username, String password) {
        Account account = accountDAO.findByUsername(username);

        if (account == null) {
            return new LoginResult(false, "用户名不存在", null);
        }

        if (account.isLocked()) {
            return new LoginResult(false, "账户已锁定，请联系管理员", null);
        }

        if (account.isExpired()) {
            return new LoginResult(false, "账户已过期", null);
        }

        String inputHash = MD5Util.encrypt(password);
        if (!inputHash.equals(account.getPasswordHash())) {
            accountDAO.incrementLoginAttempts(account.getUserId());
            int attempts = account.getLoginAttempts() + 1;

            if (attempts >= maxAttempts) {
                accountDAO.updateStatus(account.getUserId(), "锁定");
                return new LoginResult(false,
                        "登录失败次数过多，账户已锁定（可于" + (lockDuration / 60) + "分钟后自动解锁）", null);
            }

            return new LoginResult(false,
                    "密码错误，剩余尝试次数：" + (maxAttempts - attempts), null);
        }

        // 登录成功
        accountDAO.resetLoginAttempts(account.getUserId());
        accountDAO.updateLastLoginTime(account.getUserId());
        account.setLastLoginTime(new Date());
        account.setLoginAttempts(0);

        return new LoginResult(true, "登录成功", account);
    }

    /**
     * 修改密码
     */
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        Account account = accountDAO.findById(userId);
        if (account == null) return false;

        String oldHash = MD5Util.encrypt(oldPassword);
        if (!oldHash.equals(account.getPasswordHash())) {
            return false;
        }

        String newHash = MD5Util.encrypt(newPassword);
        accountDAO.updatePassword(userId, newHash);
        return true;
    }

    /**
     * 检查权限
     */
    public boolean checkPermission(String userId, String requiredRole) {
        Account account = accountDAO.findById(userId);
        if (account == null) return false;
        return account.getRoleType().equals(requiredRole);
    }

    /**
     * 登录结果封装类
     */
    public static class LoginResult {
        private boolean success;
        private String message;
        private Account account;

        public LoginResult(boolean success, String message, Account account) {
            this.success = success;
            this.message = message;
            this.account = account;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Account getAccount() { return account; }
    }
}