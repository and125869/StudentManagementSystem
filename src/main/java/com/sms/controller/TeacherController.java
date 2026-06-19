package com.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 教师控制器
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping("/main")
    public String main() {
        return "teacher/main";
    }
}