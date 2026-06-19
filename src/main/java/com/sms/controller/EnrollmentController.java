package com.sms.controller;

import com.sms.entity.Course;
import com.sms.entity.Enrollment;
import com.sms.service.CourseService;
import com.sms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 选课控制器
 */
@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;

    /**
     * 选课页面 - 展示可选课程列表
     */
    @GetMapping("/select")
    public String selectPage(Model model, HttpSession session) {
        String studentId = (String) session.getAttribute("userId");
        List<Course> openCourses = courseService.findOpenCourses();
        List<Enrollment> myEnrollments = enrollmentService.findByStudent(studentId);

        model.addAttribute("courses", openCourses);
        model.addAttribute("myEnrollments", myEnrollments);
        model.addAttribute("studentId", studentId);
        return "enrollment/select";
    }

    /**
     * 提交选课
     */
    @PostMapping("/submit")
    public String submit(@RequestParam("courseId") String courseId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        String studentId = (String) session.getAttribute("userId");
        String semester = com.sms.util.DateUtil.getCurrentSemester();

        String error = enrollmentService.submitEnrollment(studentId, courseId, semester);

        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
        } else {
            redirectAttributes.addFlashAttribute("success", "选课成功！");
        }
        return "redirect:/enrollment/select";
    }

    /**
     * 退选
     */
    @PostMapping("/cancel")
    public String cancel(@RequestParam("enrollmentId") String enrollmentId,
                         RedirectAttributes redirectAttributes) {
        String error = enrollmentService.cancelEnrollment(enrollmentId);

        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
        } else {
            redirectAttributes.addFlashAttribute("success", "退选成功！");
        }
        return "redirect:/enrollment/select";
    }

    /**
     * 查看我的选课
     */
    @GetMapping("/my")
    public String myEnrollments(Model model, HttpSession session) {
        String studentId = (String) session.getAttribute("userId");
        List<Enrollment> enrollments = enrollmentService.findByStudent(studentId);

        model.addAttribute("enrollments", enrollments);
        return "enrollment/my";
    }
}