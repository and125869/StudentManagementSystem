package com.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 学生信息管理系统 - 启动类
 */
@SpringBootApplication
@MapperScan("com.sms.dao")
public class SmsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
        System.out.println("========================================");
        System.out.println("  小型学生信息管理系统 启动成功!");
        System.out.println("  访问地址: http://localhost:8080/sms/");
        System.out.println("========================================");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SmsApplication.class);
    }
}