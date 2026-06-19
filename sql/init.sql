-- ============================================================
-- 小型学生信息管理系统 - 数据库初始化脚本
-- 数据库: MySQL 8.0
-- 字符集: utf8mb4
-- ============================================================

CREATE DATABASE IF NOT EXISTS student_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE student_db;

-- -----------------------------------------------------------
-- 1. 班级表 (Class)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_class;
CREATE TABLE t_class (
    class_id    VARCHAR(20)  PRIMARY KEY COMMENT '班级编号',
    class_name  VARCHAR(50)  NOT NULL COMMENT '班级名称',
    major       VARCHAR(50)  NOT NULL COMMENT '专业',
    grade       VARCHAR(10)  NOT NULL COMMENT '年级',
    dept_code   VARCHAR(20)  NOT NULL COMMENT '院系代码',
    student_count INT DEFAULT 0 COMMENT '学生人数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- -----------------------------------------------------------
-- 2. 学生表 (Student)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_student;
CREATE TABLE t_student (
    student_id      VARCHAR(20)  PRIMARY KEY COMMENT '学号',
    name            VARCHAR(30)  NOT NULL COMMENT '姓名',
    gender          VARCHAR(4)   NOT NULL COMMENT '性别',
    birth_date      DATE         COMMENT '出生日期',
    dept_code       VARCHAR(20)  COMMENT '院系代码',
    class_id        VARCHAR(20)  COMMENT '班级编号',
    major           VARCHAR(50)  COMMENT '专业',
    enrollment_year INT          COMMENT '入学年份',
    phone           VARCHAR(20)  COMMENT '电话',
    email           VARCHAR(50)  COMMENT '邮箱',
    status          VARCHAR(10)  DEFAULT '在校' COMMENT '状态: 在校/休学/毕业/退学',
    FOREIGN KEY (class_id) REFERENCES t_class(class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- -----------------------------------------------------------
-- 3. 教师表 (Teacher)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_teacher;
CREATE TABLE t_teacher (
    teacher_id  VARCHAR(20)  PRIMARY KEY COMMENT '工号',
    name        VARCHAR(30)  NOT NULL COMMENT '姓名',
    gender      VARCHAR(4)   COMMENT '性别',
    title       VARCHAR(20)  COMMENT '职称',
    dept_code   VARCHAR(20)  COMMENT '院系代码',
    phone       VARCHAR(20)  COMMENT '电话',
    email       VARCHAR(50)  COMMENT '邮箱'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- -----------------------------------------------------------
-- 4. 课程表 (Course)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_course;
CREATE TABLE t_course (
    course_id       VARCHAR(20)   PRIMARY KEY COMMENT '课程编号',
    course_name     VARCHAR(100)  NOT NULL COMMENT '课程名称',
    credit          DECIMAL(3,1)  NOT NULL COMMENT '学分',
    hours           INT           NOT NULL COMMENT '学时',
    dept_code       VARCHAR(20)   COMMENT '开课院系',
    teacher_id      VARCHAR(20)   COMMENT '任课教师工号',
    capacity        INT           DEFAULT 60 COMMENT '容量上限',
    enrolled_count  INT           DEFAULT 0  COMMENT '已选人数',
    schedule_time   VARCHAR(100)  COMMENT '上课时间',
    location        VARCHAR(50)   COMMENT '上课地点',
    prerequisite    VARCHAR(200)  COMMENT '先修课程要求',
    semester        VARCHAR(20)   COMMENT '开课学期',
    status          VARCHAR(10)   DEFAULT '开放' COMMENT '状态: 开放/关闭/已满',
    FOREIGN KEY (teacher_id) REFERENCES t_teacher(teacher_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- -----------------------------------------------------------
-- 5. 选课记录表 (Enrollment)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_enrollment;
CREATE TABLE t_enrollment (
    enrollment_id   VARCHAR(30)   PRIMARY KEY COMMENT '选课记录ID',
    student_id      VARCHAR(20)   NOT NULL COMMENT '学号',
    course_id       VARCHAR(20)   NOT NULL COMMENT '课程编号',
    semester        VARCHAR(20)   COMMENT '学期',
    enroll_time     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
    status          VARCHAR(10)   DEFAULT '待处理' COMMENT '状态: 待处理/已选/已退/审核中',
    weight          INT           DEFAULT 0  COMMENT '权重值',
    round           INT           DEFAULT 1  COMMENT '选课轮次',
    FOREIGN KEY (student_id) REFERENCES t_student(student_id),
    FOREIGN KEY (course_id)  REFERENCES t_course(course_id),
    UNIQUE KEY uk_stu_course_sem (student_id, course_id, semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课记录表';

-- -----------------------------------------------------------
-- 6. 成绩表 (Grade)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_grade;
CREATE TABLE t_grade (
    grade_id    VARCHAR(30)   PRIMARY KEY COMMENT '成绩ID',
    student_id  VARCHAR(20)   NOT NULL COMMENT '学号',
    course_id   VARCHAR(20)   NOT NULL COMMENT '课程编号',
    teacher_id  VARCHAR(20)   COMMENT '录入教师工号',
    score       DECIMAL(5,2)  COMMENT '分数(0-100)',
    grade_level VARCHAR(10)   COMMENT '等级: 优秀/良好/中等/及格/不及格',
    gpa         DECIMAL(3,1)  COMMENT '绩点',
    entry_time  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
    status      VARCHAR(10)   DEFAULT '草稿' COMMENT '状态: 草稿/已提交/已审核/已公布',
    FOREIGN KEY (student_id) REFERENCES t_student(student_id),
    FOREIGN KEY (course_id)  REFERENCES t_course(course_id),
    FOREIGN KEY (teacher_id) REFERENCES t_teacher(teacher_id),
    UNIQUE KEY uk_stu_course (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成绩表';

-- -----------------------------------------------------------
-- 7. 用户账号表 (Account)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_account;
CREATE TABLE t_account (
    user_id         VARCHAR(20)   PRIMARY KEY COMMENT '用户ID',
    username        VARCHAR(30)   NOT NULL UNIQUE COMMENT '用户名',
    password_hash   VARCHAR(64)   NOT NULL COMMENT '密码(MD5加盐)',
    role_type       VARCHAR(20)   NOT NULL COMMENT '角色: student/teacher/academic_admin/system_admin',
    account_status  VARCHAR(10)   DEFAULT '正常' COMMENT '状态: 正常/锁定/过期',
    login_attempts  INT           DEFAULT 0  COMMENT '登录失败次数',
    last_login_time DATETIME      COMMENT '最后登录时间',
    create_time     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    salt            VARCHAR(32)   NOT NULL COMMENT '密码盐值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账号表';

-- -----------------------------------------------------------
-- 8. 权限表 (Permission)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS t_permission;
CREATE TABLE t_permission (
    perm_id     VARCHAR(20)  PRIMARY KEY COMMENT '权限ID',
    role_type   VARCHAR(20)  NOT NULL COMMENT '角色类型',
    resource    VARCHAR(100) NOT NULL COMMENT '资源路径',
    action      VARCHAR(20)  NOT NULL COMMENT '操作: GET/POST/PUT/DELETE',
    UNIQUE KEY uk_role_res (role_type, resource, action)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ============================================================
-- 初始数据
-- ============================================================

-- 班级
INSERT INTO t_class VALUES ('C2024001', '软件工程2401', '软件工程', '2024', 'D001', 0);
INSERT INTO t_class VALUES ('C2024002', '计算机科学2401', '计算机科学', '2024', 'D001', 0);

-- 教师
INSERT INTO t_teacher VALUES ('T2024001', '张教授', '男', '教授', 'D001', '13800000001', 'zhang@school.edu.cn');
INSERT INTO t_teacher VALUES ('T2024002', '李副教授', '女', '副教授', 'D001', '13800000002', 'li@school.edu.cn');

-- 课程
INSERT INTO t_course VALUES ('CS101', '面向对象程序设计', 4.0, 64, 'D001', 'T2024001', 60, 0, '周一 1-2节', 'B101', NULL, '2024-2025-1', '开放');
INSERT INTO t_course VALUES ('CS102', '数据库原理', 3.0, 48, 'D001', 'T2024002', 60, 0, '周三 3-4节', 'B102', NULL, '2024-2025-1', '开放');
INSERT INTO t_course VALUES ('CS201', '数据结构', 4.0, 64, 'D001', 'T2024002', 50, 0, '周二 5-6节', 'B201', 'CS101', '2024-2025-1', '开放');

-- 学生
INSERT INTO t_student VALUES ('2024001001', '张三', '男', '2005-06-15', 'D001', 'C2024001', '软件工程', 2024, '13900000001', 'zhangsan@stu.edu.cn', '在校');
INSERT INTO t_student VALUES ('2024001002', '李四', '女', '2005-03-20', 'D001', 'C2024001', '软件工程', 2024, '13900000002', 'lisi@stu.edu.cn', '在校');
INSERT INTO t_student VALUES ('2024002001', '王五', '男', '2005-09-10', 'D001', 'C2024002', '计算机科学', 2024, '13900000003', 'wangwu@stu.edu.cn', '在校');

-- 账号 (密码为: 123456)
INSERT INTO t_account VALUES ('A001', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', 'student', '正常', 0, NULL, NOW(), 's1a2l3t4');
INSERT INTO t_account VALUES ('A002', 'lisi', 'e10adc3949ba59abbe56e057f20f883e', 'student', '正常', 0, NULL, NOW(), 's1a2l3t4');
INSERT INTO t_account VALUES ('A003', 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', 'student', '正常', 0, NULL, NOW(), 's1a2l3t4');
INSERT INTO t_account VALUES ('A004', 'teacher1', 'e10adc3949ba59abbe56e057f20f883e', 'teacher', '正常', 0, NULL, NOW(), 's1a2l3t4');
INSERT INTO t_account VALUES ('A005', 'teacher2', 'e10adc3949ba59abbe56e057f20f883e', 'teacher', '正常', 0, NULL, NOW(), 's1a2l3t4');
INSERT INTO t_account VALUES ('A006', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'academic_admin', '正常', 0, NULL, NOW(), 's1a2l3t4');

-- 权限
INSERT INTO t_permission VALUES ('P01', 'student', '/student/**', 'GET');
INSERT INTO t_permission VALUES ('P02', 'student', '/course/list', 'GET');
INSERT INTO t_permission VALUES ('P03', 'student', '/enrollment/**', 'POST');
INSERT INTO t_permission VALUES ('P04', 'teacher', '/grade/**', 'POST');
INSERT INTO t_permission VALUES ('P05', 'teacher', '/grade/**', 'GET');
INSERT INTO t_permission VALUES ('P06', 'teacher', '/course/teaching', 'GET');
INSERT INTO t_permission VALUES ('P07', 'academic_admin', '/**', 'GET');
INSERT INTO t_permission VALUES ('P08', 'academic_admin', '/**', 'POST');
INSERT INTO t_permission VALUES ('P09', 'academic_admin', '/**', 'PUT');
INSERT INTO t_permission VALUES ('P10', 'academic_admin', '/**', 'DELETE');