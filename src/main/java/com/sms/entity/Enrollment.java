package com.sms.entity;

import java.util.Date;

/**
 * 选课记录实体类
 */
public class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String courseId;
    private String semester;
    private Date enrollTime;
    private String status;
    private Integer weight;
    private Integer round;

    public Enrollment() {}

    public Enrollment(String studentId, String courseId, String semester) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
    }

    // 业务方法
    public boolean isPending() {
        return "待处理".equals(status);
    }

    public boolean isSelected() {
        return "已选".equals(status);
    }

    public boolean isCancelled() {
        return "已退".equals(status);
    }

    // Getters and Setters
    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public Date getEnrollTime() { return enrollTime; }
    public void setEnrollTime(Date enrollTime) { this.enrollTime = enrollTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
    public Integer getRound() { return round; }
    public void setRound(Integer round) { this.round = round; }

    @Override
    public String toString() {
        return "Enrollment{" + "enrollmentId='" + enrollmentId + '\'' +
               ", studentId='" + studentId + "', courseId='" + courseId + '\'' + '}';
    }
}