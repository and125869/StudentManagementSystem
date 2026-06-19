package com.sms.controller;

import com.sms.entity.Course;
import com.sms.entity.Enrollment;
import com.sms.entity.Grade;
import com.sms.entity.Student;
import com.sms.service.CourseService;
import com.sms.service.EnrollmentService;
import com.sms.service.GradeService;
import com.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 成绩控制器
 */
@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    /**
     * 教师 - 成绩录入主页面
     */
    @GetMapping("/entry")
    public String entryPage(Model model, HttpSession session) {
        String teacherId = (String) session.getAttribute("userId");
        List<Course> courses = courseService.findByTeacher(teacherId);
        model.addAttribute("courses", courses);
        return "grade/entry";
    }

    /**
     * 教师 - 选择课程后显示学生名单
     */
    @GetMapping("/entry/{courseId}")
    public String entryForm(@PathVariable("courseId") String courseId,
                            Model model, HttpSession session) {
        String teacherId = (String) session.getAttribute("userId");
        Course course = courseService.findById(courseId);

        if (course == null || !course.getTeacherId().equals(teacherId)) {
            return "redirect:/grade/entry";
        }

        List<Enrollment> enrollments = enrollmentService.findByCourse(courseId);
        List<Grade> existingGrades = gradeService.findByCourse(courseId);
        List<Student> students = new ArrayList<>();

        for (Enrollment e : enrollments) {
            if (e.isSelected()) {
                Student s = studentService.findById(e.getStudentId());
                if (s != null) students.add(s);
            }
        }

        model.addAttribute("course", course);
        model.addAttribute("students", students);
        model.addAttribute("existingGrades", existingGrades);
        return "grade/entryForm";
    }

    /**
     * 教师 - 提交成绩
     */
    @PostMapping("/submit")
    public String submit(@RequestParam("courseId") String courseId,
                         @RequestParam("teacherId") String teacherId,
                         @RequestParam("studentIds") List<String> studentIds,
                         @RequestParam("scores") List<Double> scores,
                         RedirectAttributes redirectAttributes) {
        List<Grade> gradeList = new ArrayList<>();
        for (int i = 0; i < studentIds.size(); i++) {
            if (scores.get(i) != null && scores.get(i) >= 0) {
                Grade grade = new Grade(studentIds.get(i), courseId, teacherId);
                grade.setScore(scores.get(i));
                gradeList.add(grade);
            }
        }

        String result = gradeService.batchEnterGrades(gradeList);
        redirectAttributes.addFlashAttribute("success", result);
        return "redirect:/grade/entry";
    }

    /**
     * 教师 - 修改单条成绩
     */
    @PostMapping("/modify")
    public String modify(@RequestParam("gradeId") String gradeId,
                         @RequestParam("score") Double score,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        String teacherId = (String) session.getAttribute("userId");
        String error = gradeService.modifyGrade(gradeId, score, teacherId);

        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
        } else {
            redirectAttributes.addFlashAttribute("success", "成绩修改成功");
        }
        return "redirect:/grade/entry";
    }

    /**
     * 学生 - 查看成绩
     */
    @GetMapping("/my")
    public String myGrades(Model model, HttpSession session) {
        String studentId = (String) session.getAttribute("userId");
        List<Grade> grades = gradeService.findByStudent(studentId);
        double gpa = gradeService.calculateGPA(studentId);

        model.addAttribute("grades", grades);
        model.addAttribute("gpa", String.format("%.2f", gpa));
        return "grade/my";
    }

    /**
     * 管理员 - 成绩审核
     */
    @GetMapping("/audit")
    public String auditPage(Model model) {
        // 显示所有待审核成绩
        List<Grade> pendingGrades = new ArrayList<>();
        // 简化：查询所有教师的成绩
        List<Grade> allGrades = new ArrayList<>();
        model.addAttribute("grades", allGrades);
        return "grade/audit";
    }
}