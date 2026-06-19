<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>学生信息管理系统</h1>
        <div class="user-info">
            <%= session.getAttribute("username") %>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm">退出</a>
        </div>
    </div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/student/main">首页</a>
        <a href="${pageContext.request.contextPath}/student/profile" class="active">个人信息</a>
        <a href="${pageContext.request.contextPath}/enrollment/select">课程选课</a>
        <a href="${pageContext.request.contextPath}/enrollment/my">我的选课</a>
        <a href="${pageContext.request.contextPath}/grade/my">我的成绩</a>
    </div>

    <div class="content" style="max-width:600px; margin:0 auto;">
        <h2>个人信息</h2>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>

        <%
        Student student = (Student) request.getAttribute("student");
        if (student != null) {
        %>
        <form action="${pageContext.request.contextPath}/student/profile/update" method="post">
            <div class="form-group">
                <label>学号</label>
                <input type="text" value="<%= student.getStudentId() %>" class="form-control" disabled>
            </div>
            <div class="form-group">
                <label>姓名</label>
                <input type="text" value="<%= student.getName() %>" class="form-control" disabled>
            </div>
            <div class="form-group">
                <label>性别</label>
                <input type="text" value="<%= student.getGender() %>" class="form-control" disabled>
            </div>
            <div class="form-group">
                <label>专业</label>
                <input type="text" value="<%= student.getMajor() != null ? student.getMajor() : "" %>" class="form-control" disabled>
            </div>
            <div class="form-group">
                <label>电话</label>
                <input type="text" name="phone" value="<%= student.getPhone() != null ? student.getPhone() : "" %>" class="form-control">
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="email" name="email" value="<%= student.getEmail() != null ? student.getEmail() : "" %>" class="form-control">
            </div>
            <div class="form-group">
                <label>状态</label>
                <input type="text" value="<%= student.getStatus() %>" class="form-control" disabled>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">保存修改</button>
            </div>
        </form>
        <% } %>
    </div>
</div>
</body>
</html>