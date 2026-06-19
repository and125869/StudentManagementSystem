package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.dao.EnrollmentDAO;
import com.sms.dao.GradeDAO;
import com.sms.dao.StudentDAO;
import com.sms.entity.Course;
import com.sms.entity.Enrollment;
import com.sms.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 选课服务 - 核心业务：选课条件校验、提交选课、退选
 */
@Service
@Transactional
public class EnrollmentService {

    @Autowired
    private EnrollmentDAO enrollmentDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private GradeDAO gradeDAO;

    @Autowired
    private StudentDAO studentDAO;

    /**
     * 提交选课申请
     * @param studentId 学号
     * @param courseId 课程编号
     * @param semester 学期
     * @return 错误信息，null表示成功
     */
    public String submitEnrollment(String studentId, String courseId, String semester) {
        // 1. 校验学生状态
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            return "学生信息不存在";
        }
        if (!student.canEnroll()) {
            return "学生当前状态不可选课（状态：" + student.getStatus() + "）";
        }

        // 2. 校验课程状态
        Course course = courseDAO.findById(courseId);
        if (course == null) {
            return "课程信息不存在";
        }
        if (!course.isOpen()) {
            return "该课程当前不可选（状态：" + course.getStatus() + "）";
        }
        if (course.isFull()) {
            return "课程已满员（容量：" + course.getCapacity() + "人）";
        }

        // 3. 校验先修课程
        String prerequisite = course.getPrerequisite();
        if (prerequisite != null && !prerequisite.isEmpty()) {
            if (!hasPassedPrerequisite(studentId, prerequisite)) {
                return "未满足先修课程要求：" + prerequisite;
            }
        }

        // 4. 校验重复选课
        List<Enrollment> existing = enrollmentDAO.findByStudentAndSemester(studentId, semester);
        for (Enrollment e : existing) {
            if (e.getCourseId().equals(courseId) && e.isSelected()) {
                return "您已选修该课程，不可重复选课";
            }
        }

        // 5. 校验时间冲突
        for (Enrollment e : existing) {
            if (e.isSelected()) {
                Course enrolledCourse = courseDAO.findById(e.getCourseId());
                if (enrolledCourse != null && hasTimeConflict(course, enrolledCourse)) {
                    return "课程时间与已选课程【" + enrolledCourse.getCourseName() + "】冲突";
                }
            }
        }

        // 6. 校验学分上限
        // (简化实现：当前学期已选学分 + 本课程学分 <= 30)
        double currentCredits = 0;
        for (Enrollment e : existing) {
            if (e.isSelected()) {
                Course c = courseDAO.findById(e.getCourseId());
                if (c != null) currentCredits += c.getCredit();
            }
        }
        if (currentCredits + course.getCredit() > 30) {
            return "选课学分超限（当前已选" + currentCredits + "学分，本课程" + course.getCredit() + "学分，上限30学分）";
        }

        // 7. 执行选课
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId("E" + UUID.randomUUID().toString().substring(0, 8));
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setSemester(semester);
        enrollment.setEnrollTime(new Date());
        enrollment.setStatus("已选");
        enrollment.setWeight(0);
        enrollment.setRound(1);
        enrollmentDAO.insert(enrollment);

        // 8. 更新课程已选人数
        courseDAO.incrementEnrolled(courseId);

        return null; // 选课成功
    }

    /**
     * 退选课程
     */
    public String cancelEnrollment(String enrollmentId) {
        Enrollment enrollment = enrollmentDAO.findById(enrollmentId);
        if (enrollment == null) {
            return "选课记录不存在";
        }
        if (!enrollment.isSelected()) {
            return "该选课记录状态为'" + enrollment.getStatus() + "'，不可退选";
        }

        enrollment.setStatus("已退");
        enrollmentDAO.update(enrollment);

        // 恢复课程容量
        courseDAO.decrementEnrolled(enrollment.getCourseId());

        return null; // 退选成功
    }

    /**
     * 查询学生选课列表
     */
    public List<Enrollment> findByStudent(String studentId) {
        return enrollmentDAO.findByStudent(studentId);
    }

    /**
     * 检查先修课程是否通过
     */
    private boolean hasPassedPrerequisite(String studentId, String prerequisite) {
        // 简单实现：检查成绩表中是否有该课程且分数>=60
        List<com.sms.entity.Grade> grades = gradeDAO.findByStudent(studentId);
        for (com.sms.entity.Grade g : grades) {
            Course c = courseDAO.findById(g.getCourseId());
            if (c != null && prerequisite.contains(c.getCourseId()) && g.getScore() != null && g.getScore() >= 60) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查时间冲突（简化实现：检查scheduleTime字符串是否重叠）
     */
    private boolean hasTimeConflict(Course c1, Course c2) {
        if (c1.getScheduleTime() == null || c2.getScheduleTime() == null) return false;
        // 提取星期和节次进行比较
        String t1 = c1.getScheduleTime().replaceAll("\\s+", "");
        String t2 = c2.getScheduleTime().replaceAll("\\s+", "");
        return t1.equals(t2);
    }
}