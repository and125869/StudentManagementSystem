package com.sms.dao;

import com.sms.entity.Grade;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 成绩数据访问接口
 */
public interface GradeDAO {
    int insert(Grade grade);
    int batchInsert(@Param("list") List<Grade> gradeList);
    int update(Grade grade);
    Grade findById(@Param("gradeId") String gradeId);
    List<Grade> findByStudent(@Param("studentId") String studentId);
    List<Grade> findByCourse(@Param("courseId") String courseId);
    List<Grade> findByTeacher(@Param("teacherId") String teacherId);
    int updateStatus(@Param("gradeId") String gradeId, @Param("status") String status);
    int batchUpdateStatus(@Param("list") List<String> gradeIds, @Param("status") String status);
    Double calculateAvgScore(@Param("courseId") String courseId);
}