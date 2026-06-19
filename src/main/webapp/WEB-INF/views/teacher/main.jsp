<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>教师主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>学生信息管理系统</h1>
        <div class="user-info">
            欢迎，<%= session.getAttribute("username") %>（教师）
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm">退出</a>
        </div>
    </div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/teacher/main" class="active">首页</a>
        <a href="${pageContext.request.contextPath}/grade/entry">成绩录入</a>
        <a href="${pageContext.request.contextPath}/changePassword">修改密码</a>
    </div>

    <div class="content">
        <h2>教师工作台</h2>
        <div class="feature-grid">
            <div class="feature-card">
                <h3>成绩录入</h3>
                <p>录入和修改所授课程的学生成绩</p>
                <a href="${pageContext.request.contextPath}/grade/entry">进入</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>