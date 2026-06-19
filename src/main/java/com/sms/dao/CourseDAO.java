package com.sms.dao;

import com.sms.entity.Course;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 课程数据访问接口
 */
public interface CourseDAO {
    int insert(Course course);
    int update(Course course);
    int delete(@Param("courseId") String courseId);
    Course findById(@Param("courseId") String courseId);
    List<Course> findAll();
    List<Course> findByTeacher(@Param("teacherId") String teacherId);
    List<Course> findBySemester(@Param("semester") String semester);
    List<Course> findOpenCourses();
    List<Course> findByDept(@Param("deptCode") String deptCode);
    int incrementEnrolled(@Param("courseId") String courseId);
    int decrementEnrolled(@Param("courseId") String courseId);
}