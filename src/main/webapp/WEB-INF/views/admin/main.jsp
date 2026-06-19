<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>学生信息管理系统</h1>
        <div class="user-info">
            欢迎，<%= session.getAttribute("username") %>（管理员）
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm">退出</a>
        </div>
    </div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/admin/main" class="active">首页</a>
        <a href="${pageContext.request.contextPath}/changePassword">修改密码</a>
    </div>

    <div class="content">
        <h2>管理员工作台</h2>
        <div class="feature-grid">
            <div class="feature-card">
                <h3>学生管理</h3>
                <p>管理学生信息和学籍状态</p>
                <a href="#">进入</a>
            </div>
            <div class="feature-card">
                <h3>课程管理</h3>
                <p>管理课程信息和选课参数</p>
                <a href="#">进入</a>
            </div>
            <div class="feature-card">
                <h3>成绩审核</h3>
                <p>审核教师提交的成绩</p>
                <a href="${pageContext.request.contextPath}/grade/audit">进入</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>