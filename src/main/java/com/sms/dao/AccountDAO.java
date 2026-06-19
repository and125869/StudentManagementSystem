package com.sms.dao;

import com.sms.entity.Account;
import org.apache.ibatis.annotations.Param;

/**
 * 账号数据访问接口
 */
public interface AccountDAO {
    int insert(Account account);
    int update(Account account);
    Account findById(@Param("userId") String userId);
    Account findByUsername(@Param("username") String username);
    int updateStatus(@Param("userId") String userId, @Param("status") String status);
    int incrementLoginAttempts(@Param("userId") String userId);
    int resetLoginAttempts(@Param("userId") String userId);
    int updatePassword(@Param("userId") String userId, @Param("passwordHash") String passwordHash);
    int updateLastLoginTime(@Param("userId") String userId);
}