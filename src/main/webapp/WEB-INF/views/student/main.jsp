<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>学生信息管理系统</h1>
        <div class="user-info">
            欢迎，<%= session.getAttribute("username") %>（<%= session.getAttribute("roleType") %>）
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm">退出</a>
        </div>
    </div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/student/main" class="active">首页</a>
        <a href="${pageContext.request.contextPath}/student/profile">个人信息</a>
        <a href="${pageContext.request.contextPath}/enrollment/select">课程选课</a>
        <a href="${pageContext.request.contextPath}/enrollment/my">我的选课</a>
        <a href="${pageContext.request.contextPath}/grade/my">我的成绩</a>
        <a href="${pageContext.request.contextPath}/changePassword">修改密码</a>
    </div>

    <div class="content">
        <div class="welcome-card">
            <h2>欢迎使用学生信息管理系统</h2>
            <p>您可以使用以下功能：</p>
            <div class="feature-grid">
                <div class="feature-card">
                    <h3>查看个人信息</h3>
                    <p>查看和更新个人基本信息</p>
                    <a href="${pageContext.request.contextPath}/student/profile">进入</a>
                </div>
                <div class="feature-card">
                    <h3>课程选课</h3>
                    <p>浏览可选课程，提交选课申请</p>
                    <a href="${pageContext.request.contextPath}/enrollment/select">进入</a>
                </div>
                <div class="feature-card">
                    <h3>我的选课</h3>
                    <p>查看已选课程，管理选课记录</p>
                    <a href="${pageContext.request.contextPath}/enrollment/my">进入</a>
                </div>
                <div class="feature-card">
                    <h3>成绩查询</h3>
                    <p>查看各课程成绩及GPA</p>
                    <a href="${pageContext.request.contextPath}/grade/my">进入</a>
                </div>
            </div>
        </div>

        <% if (request.getAttribute("student") != null) { %>
            <div class="info-card">
                <h3>基本信息</h3>
                <p>学号：${student.studentId}</p>
                <p>姓名：${student.name}</p>
                <p>专业：${student.major}</p>
                <p>班级：${student.classId}</p>
            </div>
        <% } %>
    </div>
</div>
</body>
</html>