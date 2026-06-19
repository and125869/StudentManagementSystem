<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Course, com.sms.entity.Student, com.sms.entity.Grade" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成绩录入 - <%= ((Course) request.getAttribute("course")).getCourseName() %></title>
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
        <%
        Course course = (Course) request.getAttribute("course");
        %>
        <h2>成绩录入 - <%= course.getCourseName() %>（<%= course.getCourseId() %>）</h2>

        <form action="${pageContext.request.contextPath}/grade/submit" method="post">
            <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
            <input type="hidden" name="teacherId" value="<%= session.getAttribute("userId") %>">

            <table class="table">
                <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>专业</th>
                        <th>分数 (0-100)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<Student> students = (List<Student>) request.getAttribute("students");
                    if (students != null) {
                        for (Student s : students) {
                    %>
                    <tr>
                        <td><%= s.getStudentId() %></td>
                        <td><%= s.getName() %></td>
                        <td><%= s.getMajor() != null ? s.getMajor() : "-" %></td>
                        <td>
                            <input type="hidden" name="studentIds" value="<%= s.getStudentId() %>">
                            <input type="number" name="scores" min="0" max="100" step="0.5"
                                   class="form-control" style="width:100px;" placeholder="输入分数">
                        </td>
                    </tr>
                    <%
                        }
                    }
                    %>
                </tbody>
            </table>

            <div class="form-group" style="text-align:center; margin-top:20px;">
                <button type="submit" class="btn btn-primary">提交成绩</button>
                <a href="${pageContext.request.contextPath}/grade/entry" class="btn">返回</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>