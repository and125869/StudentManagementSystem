<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Course" %>
<%@ page import="com.sms.entity.Enrollment" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>课程选课</title>
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
        <a href="${pageContext.request.contextPath}/enrollment/select" class="active">课程选课</a>
        <a href="${pageContext.request.contextPath}/enrollment/my">我的选课</a>
        <a href="${pageContext.request.contextPath}/grade/my">我的成绩</a>
    </div>

    <div class="content">
        <h2>可选课程列表</h2>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>

        <table class="table">
            <thead>
                <tr>
                    <th>课程编号</th>
                    <th>课程名称</th>
                    <th>学分</th>
                    <th>学时</th>
                    <th>上课时间</th>
                    <th>地点</th>
                    <th>容量</th>
                    <th>先修课程</th>
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
                    <td><%= c.getHours() %></td>
                    <td><%= c.getScheduleTime() != null ? c.getScheduleTime() : "-" %></td>
                    <td><%= c.getLocation() != null ? c.getLocation() : "-" %></td>
                    <td><%= c.getEnrolledCount() %>/<%= c.getCapacity() %></td>
                    <td><%= c.getPrerequisite() != null ? c.getPrerequisite() : "无" %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/enrollment/submit" method="post" style="display:inline;">
                            <input type="hidden" name="courseId" value="<%= c.getCourseId() %>">
                            <button type="submit" class="btn btn-primary btn-sm">选课</button>
                        </form>
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