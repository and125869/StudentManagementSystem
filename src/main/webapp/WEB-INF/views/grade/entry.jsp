<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成绩录入</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>学生信息管理系统</h1>
        <div class="user-info">
            <%= session.getAttribute("username") %>（教师）
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm">退出</a>
        </div>
    </div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/teacher/main">首页</a>
        <a href="${pageContext.request.contextPath}/grade/entry" class="active">成绩录入</a>
    </div>

    <div class="content">
        <h2>选择课程</h2>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>

        <table class="table">
            <thead>
                <tr>
                    <th>课程编号</th>
                    <th>课程名称</th>
                    <th>学分</th>
                    <th>已选人数</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Course> courses = (List<Course>) request.getAttribute("courses");
                if (courses != null) {
                    for (Course c : courses) {
                %>
                <tr>
                    <td><%= c.getCourseId() %></td>
                    <td><%= c.getCourseName() %></td>
                    <td><%= c.getCredit() %></td>
                    <td><%= c.getEnrolledCount() %>/<%= c.getCapacity() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/grade/entry/<%= c.getCourseId() %>"
                           class="btn btn-primary btn-sm">录入成绩</a>
                    </td>
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