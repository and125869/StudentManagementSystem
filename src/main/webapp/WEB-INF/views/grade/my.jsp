<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Grade" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的成绩</title>
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
        <a href="${pageContext.request.contextPath}/enrollment/select">课程选课</a>
        <a href="${pageContext.request.contextPath}/enrollment/my">我的选课</a>
        <a href="${pageContext.request.contextPath}/grade/my" class="active">我的成绩</a>
    </div>

    <div class="content">
        <h2>成绩查询</h2>

        <%
        String gpa = (String) request.getAttribute("gpa");
        if (gpa != null) {
        %>
        <div class="info-card">
            <h3>GPA：<%= gpa %></h3>
        </div>
        <% } %>

        <table class="table">
            <thead>
                <tr>
                    <th>课程编号</th>
                    <th>分数</th>
                    <th>等级</th>
                    <th>绩点</th>
                    <th>录入时间</th>
                    <th>状态</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Grade> grades = (List<Grade>) request.getAttribute("grades");
                if (grades != null) {
                    for (Grade g : grades) {
                %>
                <tr>
                    <td><%= g.getCourseId() %></td>
                    <td><%= g.getScore() != null ? g.getScore() : "-" %></td>
                    <td><%= g.getGradeLevel() != null ? g.getGradeLevel() : "-" %></td>
                    <td><%= g.getGpa() != null ? g.getGpa() : "-" %></td>
                    <td><%= g.getEntryTime() != null ? g.getEntryTime() : "-" %></td>
                    <td><%= g.getStatus() != null ? g.getStatus() : "-" %></td>
                </tr>
                <%
                    }
                }
                %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>