package com.sms.dao;

import com.sms.entity.Student;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 学生数据访问接口
 */
public interface StudentDAO {
    int insert(Student student);
    int update(Student student);
    int delete(@Param("studentId") String studentId);
    Student findById(@Param("studentId") String studentId);
    List<Student> findByClass(@Param("classId") String classId);
    List<Student> findByDept(@Param("deptCode") String deptCode);
    List<Student> findAll();
    List<Student> findByName(@Param("name") String name);
    int updateStatus(@Param("studentId") String studentId, @Param("status") String status);
}