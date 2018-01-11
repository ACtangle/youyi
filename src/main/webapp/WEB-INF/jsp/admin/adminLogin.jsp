<%--
  Created by IntelliJ IDEA.
  User: melon
  Date: 18-1-10
  Time: 下午11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
    <form action="admin_login" method="post">
        <font color="red">${requestScope.message}</font>
        <table>
            <tr>
                <td>登录名:</td>
                <td>密码:</td>
            </tr>
            <tr>
                <td><input type="text" id="username" name="username" ></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" value="登录"></td>
            </tr>
        </table>
    </form>
</head>
<body>

</body>
</html>
