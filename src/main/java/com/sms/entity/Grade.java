package com.sms.entity;

import java.util.Date;

/**
 * 成绩实体类
 */
public class Grade {
    private String gradeId;
    private String studentId;
    private String courseId;
    private String teacherId;
    private Double score;
    private String gradeLevel;
    private Double gpa;
    private Date entryTime;
    private String status;

    public Grade() {}

    public Grade(String studentId, String courseId, String teacherId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.teacherId = teacherId;
    }

    /**
     * 根据百分制分数计算等级
     */
    public void calculateLevel() {
        if (score == null) return;
        if (score >= 90) gradeLevel = "优秀";
        else if (score >= 80) gradeLevel = "良好";
        else if (score >= 70) gradeLevel = "中等";
        else if (score >= 60) gradeLevel = "及格";
        else gradeLevel = "不及格";
    }

    /**
     * 根据分数计算绩点（4.0制）
     */
    public void calculateGpa() {
        if (score == null) return;
        if (score >= 90) gpa = 4.0;
        else if (score >= 85) gpa = 3.7;
        else if (score >= 82) gpa = 3.3;
        else if (score >= 78) gpa = 3.0;
        else if (score >= 75) gpa = 2.7;
        else if (score >= 72) gpa = 2.3;
        else if (score >= 68) gpa = 2.0;
        else if (score >= 64) gpa = 1.5;
        else if (score >= 60) gpa = 1.0;
        else gpa = 0.0;
    }

    /**
     * 校验分数范围
     */
    public boolean validateScore(int minScore, int maxScore) {
        return score != null && score >= minScore && score <= maxScore;
    }

    // Getters and Setters
    public String getGradeId() { return gradeId; }
    public void setGradeId(String gradeId) { this.gradeId = gradeId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public String getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
    public Date getEntryTime() { return entryTime; }
    public void setEntryTime(Date entryTime) { this.entryTime = entryTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Grade{" + "gradeId='" + gradeId + "', score=" + score + '}';
    }
}