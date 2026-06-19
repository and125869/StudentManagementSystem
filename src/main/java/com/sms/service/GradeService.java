package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.dao.EnrollmentDAO;
import com.sms.dao.GradeDAO;
import com.sms.entity.Course;
import com.sms.entity.Enrollment;
import com.sms.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 成绩管理服务 - 录入、修改、查询、统计
 */
@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeDAO gradeDAO;

    @Autowired
    private EnrollmentDAO enrollmentDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Value("${app.grade.min-score:0}")
    private int minScore;

    @Value("${app.grade.max-score:100}")
    private int maxScore;

    /**
     * 录入单条成绩
     */
    public String enterGrade(Grade grade) {
        // 校验分数范围
        if (!grade.validateScore(minScore, maxScore)) {
            return "分数必须在" + minScore + "-" + maxScore + "之间";
        }

        // 校验教师是否该课程授课教师
        Course course = courseDAO.findById(grade.getCourseId());
        if (course == null) {
            return "课程不存在";
        }
        if (!course.getTeacherId().equals(grade.getTeacherId())) {
            return "您不是该课程的授课教师，无权录入成绩";
        }

        // 校验学生是否选修了该课程
        List<Enrollment> enrollments = enrollmentDAO.findByCourse(grade.getCourseId());
        boolean enrolled = enrollments.stream().anyMatch(e ->
                e.getStudentId().equals(grade.getStudentId()) && e.isSelected());
        if (!enrolled) {
            return "该学生未选修此课程";
        }

        // 计算等级和绩点
        grade.calculateLevel();
        grade.calculateGpa();

        // 保存成绩
        grade.setGradeId("G" + UUID.randomUUID().toString().substring(0, 8));
        grade.setEntryTime(new Date());
        grade.setStatus("已提交");
        gradeDAO.insert(grade);

        return null; // 成功
    }

    /**
     * 批量录入成绩
     */
    public String batchEnterGrades(List<Grade> gradeList) {
        List<Grade> validGrades = new ArrayList<>();
        for (Grade grade : gradeList) {
            if (grade.validateScore(minScore, maxScore)) {
                grade.setGradeId("G" + UUID.randomUUID().toString().substring(0, 8));
                grade.setEntryTime(new Date());
                grade.setStatus("已提交");
                grade.calculateLevel();
                grade.calculateGpa();
                validGrades.add(grade);
            }
        }
        if (!validGrades.isEmpty()) {
            gradeDAO.batchInsert(validGrades);
        }
        return "成功录入" + validGrades.size() + "条成绩";
    }

    /**
     * 修改成绩
     */
    public String modifyGrade(String gradeId, Double newScore, String teacherId) {
        Grade grade = gradeDAO.findById(gradeId);
        if (grade == null) {
            return "成绩记录不存在";
        }

        // 校验修改权限
        if (!grade.getTeacherId().equals(teacherId)) {
            return "只能修改自己录入的成绩";
        }

        if (newScore < minScore || newScore > maxScore) {
            return "分数必须在" + minScore + "-" + maxScore + "之间";
        }

        grade.setScore(newScore);
        grade.calculateLevel();
        grade.calculateGpa();
        grade.setStatus("已修改");
        gradeDAO.update(grade);

        return null; // 成功
    }

    /**
     * 按学生查询成绩
     */
    public List<Grade> findByStudent(String studentId) {
        return gradeDAO.findByStudent(studentId);
    }

    /**
     * 按课程查询成绩
     */
    public List<Grade> findByCourse(String courseId) {
        return gradeDAO.findByCourse(courseId);
    }

    /**
     * 按教师查询成绩
     */
    public List<Grade> findByTeacher(String teacherId) {
        return gradeDAO.findByTeacher(teacherId);
    }

    /**
     * 计算学生GPA
     */
    public double calculateGPA(String studentId) {
        List<Grade> grades = gradeDAO.findByStudent(studentId);
        if (grades.isEmpty()) return 0.0;

        double totalWeightedGpa = 0;
        double totalCredits = 0;

        for (Grade grade : grades) {
            if (grade.getScore() != null) {
                Course course = courseDAO.findById(grade.getCourseId());
                if (course != null) {
                    totalWeightedGpa += grade.getGpa() * course.getCredit();
                    totalCredits += course.getCredit();
                }
            }
        }

        return totalCredits > 0 ? totalWeightedGpa / totalCredits : 0.0;
    }

    /**
     * 审核成绩
     */
    public void auditGrade(String gradeId, String status) {
        gradeDAO.updateStatus(gradeId, status);
    }

    /**
     * 批量审核
     */
    public void batchAudit(List<String> gradeIds, String status) {
        gradeDAO.batchUpdateStatus(gradeIds, status);
    }
}