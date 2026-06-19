package com.sms.dao;

import com.sms.entity.Enrollment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 选课记录数据访问接口
 */
public interface EnrollmentDAO {
    int insert(Enrollment enrollment);
    int update(Enrollment enrollment);
    int delete(@Param("enrollmentId") String enrollmentId);
    Enrollment findById(@Param("enrollmentId") String enrollmentId);
    List<Enrollment> findByStudent(@Param("studentId") String studentId);
    List<Enrollment> findByCourse(@Param("courseId") String courseId);
    List<Enrollment> findByStudentAndSemester(@Param("studentId") String studentId, @Param("semester") String semester);
    int updateStatus(@Param("enrollmentId") String enrollmentId, @Param("status") String status);
    int countByCourse(@Param("courseId") String courseId);
    int deleteByStudentAndCourse(@Param("studentId") String studentId, @Param("courseId") String courseId);
}