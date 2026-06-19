package com.sms.entity;

import java.util.Date;

/**
 * 用户账号实体类
 */
public class Account {
    private String userId;
    private String username;
    private String passwordHash;
    private String roleType;
    private String accountStatus;
    private Integer loginAttempts;
    private Date lastLoginTime;
    private Date createTime;
    private String salt;

    public Account() {}

    // 业务方法
    public boolean isLocked() {
        return "锁定".equals(accountStatus);
    }

    public boolean isExpired() {
        return "过期".equals(accountStatus);
    }

    public boolean isActive() {
        return "正常".equals(accountStatus);
    }

    public boolean isAdmin() {
        return "academic_admin".equals(roleType) || "system_admin".equals(roleType);
    }

    public boolean isTeacher() {
        return "teacher".equals(roleType);
    }

    public boolean isStudent() {
        return "student".equals(roleType);
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRoleType() { return roleType; }
    public void setRoleType(String roleType) { this.roleType = roleType; }
    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
    public Integer getLoginAttempts() { return loginAttempts; }
    public void setLoginAttempts(Integer loginAttempts) { this.loginAttempts = loginAttempts; }
    public Date getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(Date lastLoginTime) { this.lastLoginTime = lastLoginTime; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    @Override
    public String toString() {
        return "Account{" + "username='" + username + "', role='" + roleType + '\'' + '}';
    }
}