package com.sms.dao;

import com.sms.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 教师数据访问接口
 */
public interface TeacherDAO {
    int insert(Teacher teacher);
    int update(Teacher teacher);
    Teacher findById(@Param("teacherId") String teacherId);
    List<Teacher> findAll();
    List<Teacher> findByDept(@Param("deptCode") String deptCode);
}