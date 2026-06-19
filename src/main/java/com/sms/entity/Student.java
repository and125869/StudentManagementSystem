package com.sms.entity;

import java.util.Date;

/**
 * 学生实体类
 */
public class Student {
    private String studentId;
    private String name;
    private String gender;
    private Date birthDate;
    private String deptCode;
    private String classId;
    private String major;
    private Integer enrollmentYear;
    private String phone;
    private String email;
    private String status;

    public Student() {}

    public Student(String studentId, String name, String gender) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
    }

    // 业务方法
    public boolean isActive() {
        return "在校".equals(status);
    }

    public boolean canEnroll() {
        return isActive() && !"休学".equals(status) && !"退学".equals(status);
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public Integer getEnrollmentYear() { return enrollmentYear; }
    public void setEnrollmentYear(Integer enrollmentYear) { this.enrollmentYear = enrollmentYear; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Student{" + "studentId='" + studentId + "', name='" + name + '\'' + '}';
    }
}