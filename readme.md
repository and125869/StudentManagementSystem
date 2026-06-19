# 小型学生信息管理系统

基于 **Spring Boot 2.7.18 + MyBatis + MySQL 8.0** 的学生信息管理系统，支持学生、教师、管理员三类角色的登录与操作，涵盖学生管理、课程选课、成绩管理、权限控制等核心功能。

---

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 1.8 | 开发语言 |
| Spring Boot | 2.7.18 | 应用框架 |
| MyBatis | 2.3.1 | ORM 框架 |
| MySQL | 8.0 | 关系型数据库 |
| Druid | 1.2.18 | 数据库连接池 |
| JSP / JSTL | 1.2 | 前端视图 |
| Maven | 3.x | 项目构建 |

---

## 项目结构

```
系统实现/
├── pom.xml
├── sql/
│   └── init.sql                 # 数据库建表 + 测试数据
└── src/main/
    ├── java/com/sms/
    │   ├── SmsApplication.java  # 启动类
    │   ├── config/
    │   │   ├── WebConfig.java          # 拦截器注册
    │   │   └── LoginInterceptor.java   # 登录拦截器
    │   ├── controller/
    │   │   ├── LoginController.java      # 登录/登出/改密
    │   │   ├── StudentController.java    # 学生主页/个人信息
    │   │   ├── TeacherController.java    # 教师主页
    │   │   ├── AdminController.java      # 管理员主页
    │   │   ├── EnrollmentController.java # 选课/退选
    │   │   └── GradeController.java      # 成绩录入/查询
    │   ├── service/
    │   │   ├── AuthService.java          # 认证（密码校验/锁定）
    │   │   ├── StudentService.java       # 学生管理
    │   │   ├── CourseService.java        # 课程管理
    │   │   ├── EnrollmentService.java    # 选课（含7项业务校验）
    │   │   └── GradeService.java         # 成绩（评分/绩点计算）
    │   ├── dao/          # 7个DAO接口（Account, Course, Enrollment, Grade, Student, Teacher, Class）
    │   ├── entity/       # 8个实体类（含业务方法）
    │   └── util/
    │       ├── MD5Util.java   # MD5加密
    │       ├── DateUtil.java  # 日期/学期工具
    │       └── AuthUtil.java  # Session鉴权
    ├── resources/
    │   ├── application.yml        # 应用配置
    │   ├── mappers/               # 5个MyBatis XML映射文件
    │   └── static/css/style.css   # 样式表
    └── webapp/WEB-INF/views/
        ├── login.jsp              # 登录页
        ├── changePassword.jsp     # 修改密码
        ├── student/
        │   ├── main.jsp           # 学生主页
        │   └── profile.jsp        # 个人信息
        ├── teacher/
        │   └── main.jsp           # 教师主页
        ├── admin/
        │   └── main.jsp           # 管理员主页
        ├── enrollment/
        │   ├── select.jsp         # 选课页
        │   └── my.jsp             # 我的选课
        └── grade/
            ├── my.jsp             # 成绩查询
            ├── entry.jsp          # 成绩录入-课程选择
            └── entryForm.jsp      # 成绩录入-学生名单
```

---

## 数据库设计

共 8 张表，通过外键关联：

| 表名 | 说明 | 关键字段 |
|------|------|----------|
| `t_class` | 班级表 | class_id, class_name, major |
| `t_student` | 学生表 | student_id, name, class_id, status |
| `t_teacher` | 教师表 | teacher_id, name, title, dept_code |
| `t_course` | 课程表 | course_id, teacher_id, capacity, enrolled_count, prerequisite |
| `t_enrollment` | 选课记录表 | enrollment_id, student_id, course_id, weight, round |
| `t_grade` | 成绩表 | grade_id, student_id, course_id, score, grade_level, gpa |
| `t_account` | 用户账号表 | user_id, username, password_hash, role_type, login_attempts |
| `t_permission` | 权限表 | role_type, resource, action |

---

## 功能模块

### 1. 用户认证

- 登录：用户名 + 密码（MD5 加密），验证通过后根据角色跳转对应主页
- 登出：清除 Session
- 密码修改：验证原密码后修改
- 安全机制：连续 5 次登录失败自动锁定账户
- 登录拦截器：未登录用户自动跳转至登录页

### 2. 学生管理

- 查看个人信息（学号、姓名、专业、班级、状态）
- 修改联系方式（电话、邮箱）
- 学籍状态校验：在校状态方可选课

### 3. 课程选课

选课时执行 7 项业务规则校验：

1. 学生状态校验 —— 在校状态方可选课
2. 课程状态校验 —— 课程须为"开放"状态
3. 课程容量校验 —— 已选人数 < 容量上限
4. 先修课程校验 —— 必须已通过先修课程
5. 重复选课校验 —— 同课程不可重复选课
6. 时间冲突校验 —— 选课时间不可冲突
7. 学分上限校验 —— 单学期学分不超过上限

支持退选操作，退选后释放课程容量。

### 4. 成绩管理

- 教师录入：选择课程后查看学生名单，批量录入百分制分数
- 教师修改：修改已录入的成绩
- 自动计算：根据分数自动计算等级（优秀/良好/中等/及格/不及格）和绩点
- GPA 计算：按学分加权计算 GPA（4.0 制）
- 学生查询：查看个人所有课程成绩及 GPA

### 5. 权限控制

- 基于角色的权限表（`t_permission`）
- 登录拦截器控制未登录访问
- 三种角色：student（学生）、teacher（教师）、academic_admin（教务管理员）

---

## 快速开始

### 环境要求

- JDK 1.8+
- MySQL 8.0+
- Maven 3.x

### 1. 初始化数据库

执行 `sql/init.sql` 脚本：

```bash
mysql -u root -p < sql/init.sql
```

### 2. 修改数据库配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_db?useUnicode=true&characterEncoding=utf8mb4&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

### 3. 编译打包

```bash
mvn clean package -DskipTests
```

### 4. 运行

**方式一：独立运行**

```bash
java -jar target/student-management-1.0.0.war
```

**方式二：部署到 Tomcat**

将 `target/student-management-1.0.0.war` 复制到 Tomcat 的 `webapps` 目录。

### 5. 访问

浏览器打开 `http://localhost:8080/sms/`

---

## 测试账号

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| `zhangsan` | `123456` | 学生 | 学号 2024001001 |
| `lisi` | `123456` | 学生 | 学号 2024001002 |
| `wangwu` | `123456` | 学生 | 学号 2024002001 |
| `teacher1` | `123456` | 教师 | 工号 T2024001 |
| `teacher2` | `123456` | 教师 | 工号 T2024002 |
| `admin` | `123456` | 管理员 | 教务管理员 |

---

## 核心配置说明

```yaml
# 业务配置
app:
  grade:
    min-score: 0       # 最低分数
    max-score: 100     # 最高分数
  login:
    max-attempts: 5    # 最大登录失败次数
    lock-duration: 1800  # 锁定时间（秒）
```

---

## 成绩等级对照

| 分数范围 | 等级 | 绩点 |
|----------|------|------|
| 90-100 | 优秀 | 4.0 |
| 85-89 | — | 3.7 |
| 82-84 | 良好 | 3.3 |
| 78-81 | — | 3.0 |
| 75-77 | 中等 | 2.7 |
| 72-74 | — | 2.3 |
| 68-71 | — | 2.0 |
| 64-67 | 及格 | 1.5 |
| 60-63 | — | 1.0 |
| 0-59 | 不及格 | 0.0 |