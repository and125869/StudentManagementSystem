package com.sms.entity;

/**
 * 课程实体类
 */
public class Course {
    private String courseId;
    private String courseName;
    private Double credit;
    private Integer hours;
    private String deptCode;
    private String teacherId;
    private Integer capacity;
    private Integer enrolledCount;
    private String scheduleTime;
    private String location;
    private String prerequisite;
    private String semester;
    private String status;

    public Course() {}

    // 业务方法
    public boolean isFull() {
        return enrolledCount != null && capacity != null && enrolledCount >= capacity;
    }

    public int getRemainingSlots() {
        if (capacity == null || enrolledCount == null) return 0;
        return Math.max(0, capacity - enrolledCount);
    }

    public boolean isOpen() {
        return "开放".equals(status);
    }

    public void incrementEnrolled() {
        if (enrolledCount == null) enrolledCount = 0;
        if (!isFull()) enrolledCount++;
        if (isFull()) status = "已满";
    }

    public void decrementEnrolled() {
        if (enrolledCount != null && enrolledCount > 0) {
            enrolledCount--;
            if ("已满".equals(status)) status = "开放";
        }
    }

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }
    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }
    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; }
    public String getScheduleTime() { return scheduleTime; }
    public void setScheduleTime(String scheduleTime) { this.scheduleTime = scheduleTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getPrerequisite() { return prerequisite; }
    public void setPrerequisite(String prerequisite) { this.prerequisite = prerequisite; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Course{" + "courseId='" + courseId + "', courseName='" + courseName + '\'' + '}';
    }
}