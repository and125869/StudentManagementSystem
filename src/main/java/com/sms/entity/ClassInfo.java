package com.sms.entity;

/**
 * 班级实体类
 */
public class ClassInfo {
    private String classId;
    private String className;
    private String major;
    private String grade;
    private String deptCode;
    private Integer studentCount;

    public ClassInfo() {}

    public ClassInfo(String classId, String className, String major) {
        this.classId = classId;
        this.className = className;
        this.major = major;
    }

    // Getters and Setters
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }

    @Override
    public String toString() {
        return "ClassInfo{" + "classId='" + classId + "', className='" + className + '\'' + '}';
    }
}