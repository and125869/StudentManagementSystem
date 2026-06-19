package com.sms.entity;

/**
 * 教师实体类
 */
public class Teacher {
    private String teacherId;
    private String name;
    private String gender;
    private String title;
    private String deptCode;
    private String phone;
    private String email;

    public Teacher() {}

    public Teacher(String teacherId, String name, String title) {
        this.teacherId = teacherId;
        this.name = name;
        this.title = title;
    }

    // Getters and Setters
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Teacher{" + "teacherId='" + teacherId + "', name='" + name + '\'' + '}';
    }
}