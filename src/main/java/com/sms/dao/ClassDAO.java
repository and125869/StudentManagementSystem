package com.sms.dao;

import com.sms.entity.ClassInfo;
import com.sms.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 班级数据访问接口
 */
public interface ClassDAO {
    int insert(ClassInfo classInfo);
    int update(ClassInfo classInfo);
    ClassInfo findById(@Param("classId") String classId);
    List<ClassInfo> findAll();
    List<ClassInfo> findByDept(@Param("deptCode") String deptCode);
    int updateStudentCount(@Param("classId") String classId, @Param("count") int count);
}