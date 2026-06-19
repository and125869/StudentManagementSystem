package com.sms.controller;

import com.sms.entity.Student;
import com.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 学生控制器
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 学生主页
     */
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        String studentId = (String) session.getAttribute("userId");
        Student student = studentService.findById(studentId);
        model.addAttribute("student", student);
        return "student/main";
    }

    /**
     * 查看个人信息
     */
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        String studentId = (String) session.getAttribute("userId");
        Student student = studentService.findById(studentId);
        model.addAttribute("student", student);
        return "student/profile";
    }

    /**
     * 修改个人信息
     */
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Student student,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        String studentId = (String) session.getAttribute("userId");
        student.setStudentId(studentId);
        boolean success = studentService.updateProfile(student);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "个人信息更新成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "更新失败");
        }
        return "redirect:/student/profile";
    }
}