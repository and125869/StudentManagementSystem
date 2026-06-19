<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Enrollment" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的选课</title>
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
        <a href="${pageContext.request.contextPath}/enrollment/my" class="active">我的选课</a>
        <a href="${pageContext.request.contextPath}/grade/my">我的成绩</a>
    </div>

    <div class="content">
        <h2>我的选课记录</h2>

        <table class="table">
            <thead>
                <tr>
                    <th>选课编号</th>
                    <th>课程编号</th>
                    <th>学期</th>
                    <th>选课时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Enrollment> enrollments = (List<Enrollment>) request.getAttribute("enrollments");
                if (enrollments != null) {
                    for (Enrollment e : enrollments) {
                %>
                <tr>
                    <td><%= e.getEnrollmentId() %></td>
                    <td><%= e.getCourseId() %></td>
                    <td><%= e.getSemester() != null ? e.getSemester() : "-" %></td>
                    <td><%= e.getEnrollTime() != null ? e.getEnrollTime() : "-" %></td>
                    <td><span class="badge <%= e.isSelected() ? "badge-success" : "badge-warning" %>">
                        <%= e.getStatus() %></span></td>
                    <td>
                        <% if (e.isSelected()) { %>
                        <form action="${pageContext.request.contextPath}/enrollment/cancel" method="post" style="display:inline;">
                            <input type="hidden" name="enrollmentId" value="<%= e.getEnrollmentId() %>">
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('确认退选该课程？')">退选</button>
                        </form>
                        <% } %>
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