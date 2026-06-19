package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程管理服务
 */
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseDAO courseDAO;

    public List<Course> findAll() {
        return courseDAO.findAll();
    }

    public Course findById(String courseId) {
        return courseDAO.findById(courseId);
    }

    public List<Course> findOpenCourses() {
        return courseDAO.findOpenCourses();
    }

    public List<Course> findByTeacher(String teacherId) {
        return courseDAO.findByTeacher(teacherId);
    }

    public boolean addCourse(Course course) {
        if (courseDAO.findById(course.getCourseId()) != null) {
            return false;
        }
        if (course.getStatus() == null) {
            course.setStatus("开放");
        }
        if (course.getEnrolledCount() == null) {
            course.setEnrolledCount(0);
        }
        courseDAO.insert(course);
        return true;
    }

    public boolean updateCourse(Course course) {
        Course existing = courseDAO.findById(course.getCourseId());
        if (existing == null) return false;
        courseDAO.update(course);
        return true;
    }

    public boolean removeCourse(String courseId) {
        Course existing = courseDAO.findById(course.getCourseId());
        if (existing == null) return false;
        courseDAO.delete(courseId);
        return true;
    }
}