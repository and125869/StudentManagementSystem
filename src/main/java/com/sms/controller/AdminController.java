package com.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理员控制器
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/main")
    public String main() {
        return "admin/main";
    }
}