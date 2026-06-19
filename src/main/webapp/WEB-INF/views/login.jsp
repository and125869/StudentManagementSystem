<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生信息管理系统 - 登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-container">
    <div class="login-box">
        <h1>小型学生信息管理系统</h1>
        <h2>用户登录</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/doLogin" method="post">
            <div class="form-group">
                <label for="username">用户名</label>
                <input type="text" id="username" name="username"
                       value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                       required placeholder="请输入用户名">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" id="password" name="password" required placeholder="请输入密码">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block">登 录</button>
            </div>
        </form>

        <div class="login-footer">
            <p>测试账号：student / teacher / admin | 密码：123456</p>
        </div>
    </div>
</div>
</body>
</html>