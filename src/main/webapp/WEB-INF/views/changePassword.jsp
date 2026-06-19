<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
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

    <div class="content" style="max-width:500px; margin:40px auto;">
        <h2>修改密码</h2>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/changePassword" method="post">
            <div class="form-group">
                <label>原密码</label>
                <input type="password" name="oldPassword" class="form-control" required>
            </div>
            <div class="form-group">
                <label>新密码</label>
                <input type="password" name="newPassword" class="form-control" required>
            </div>
            <div class="form-group">
                <label>确认新密码</label>
                <input type="password" name="confirmPassword" class="form-control" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">确认修改</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>