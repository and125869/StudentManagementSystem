package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生信息服务
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    public Student findById(String studentId) {
        return studentDAO.findById(studentId);
    }

    public List<Student> findByClass(String classId) {
        return studentDAO.findByClass(classId);
    }

    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    public boolean updateProfile(Student student) {
        Student existing = studentDAO.findById(student.getStudentId());
        if (existing == null) return false;
        studentDAO.update(student);
        return true;
    }

    public boolean updateStatus(String studentId, String status) {
        Student student = studentDAO.findById(studentId);
        if (student == null) return false;
        studentDAO.updateStatus(studentId, status);
        return true;
    }
}