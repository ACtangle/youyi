<%--
  Created by IntelliJ IDEA.
  User: melon
  Date: 18-5-17
  Time: 上午8:17
  To change this template use File | Settings | File Templates.
--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--<title>Sign in</title>--%>
<%--<form action="admin_password_update" method="post">--%>
<%--<font color="red">${requestScope.message}</font>--%>
<%--<table>--%>
<%--<tr>--%>
<%--&lt;%&ndash;<td>登录名:</td>&ndash;%&gt;--%>
<%--<td>原密码:</td>--%>
    <%--<td>新密码:</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--&lt;%&ndash;<td><input type="text" id="username" name="username" ></td>&ndash;%&gt;--%>
<%--<td><input type="password" id="password" name="password"></td>--%>
    <%--<td><input type="password" id="newpassword"  name="newpassword"></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td><input type="submit" value="修改密码"></td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</form>--%>
<%--</head>--%>
<%--<body>--%>



<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login For Youyi Shop </title>

    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="../../../youyi/assetses/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../youyi/assetses/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../../youyi/assetses/css/form-elements.css">
    <link rel="stylesheet" href="../../../youyi/assetses/css/style1.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon and touch icons -->
    <link rel="shortcut icon" href="../../../youyi/assetses/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../../../youyi/assetses/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../../../youyi/assetses/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../../../youyi/assetses/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../../../youyi/assetses/ico/apple-touch-icon-57-precomposed.png">

    <%--<style>--%>
        <%--.div .form-bottom a {--%>
            <%--margin-top:100px;--%>
        <%--}--%>
    <%--</style>--%>

</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>Youyi Shop</strong> Backup Manage System</h1>
                    <div class="description">
                        <p>
                            This is a  login form made with Youyi.
                            Welcome for you or trans to youyishop<a href="fore/home.html"><strong></strong></a>, enter and use it as you like!
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Update password</h3>
                            <p>Enter your before-password and enter new password to update your password:</p>
                            <p>Or you can return to <a href="admin_category_list">Youyi Manage System</a></p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="admin_password_update" method="post" class="login-form">
                            <%--<div class="form-group">--%>
                                <%--<label class="sr-only" for="username">Username</label>--%>
                                <%--<input type="text" name="username" placeholder="Username..." class="form-username form-control" id="username">--%>
                            <%--</div>--%>
                            <%--<div class="form-group">--%>
                                <%--<label class="sr-only" for="password">Password</label>--%>
                                <%--<input type="password" name="password" placeholder="Before Password..." class="form-password form-control" id="password">--%>
                                <%--<font color="red">${requestScope.message}</font>--%>
                            <%--</div>--%>
                                <div class="form-group">
                                    <label class="sr-only" for="newpassword">Password</label>
                                    <input type="password" name="newpassword" placeholder="New Password..." class="form-password form-control" id="newpassword">
                                </div>
                            <button type="submit" class="btn">Update!</button>


                        </form>
                    </div>
                </div>
            </div>
            <%--<div class="row">--%>
            <%--<div class="col-sm-6 col-sm-offset-3 social-login">--%>
            <%--<h3>...or login with:</h3>--%>
            <%--<div class="social-login-buttons">--%>
            <%--<a class="btn btn-link-2" href="#">--%>
            <%--<i class="fa fa-facebook"></i> Facebook--%>
            <%--</a>--%>
            <%--<a class="btn btn-link-2" href="#">--%>
            <%--<i class="fa fa-twitter"></i> Twitter--%>
            <%--</a>--%>
            <%--<a class="btn btn-link-2" href="#">--%>
            <%--<i class="fa fa-google-plus"></i> Google Plus--%>
            <%--</a>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>

</div>


<!-- Javascript -->
<script src="../../../youyi/assetses/js/jquery-1.11.1.min.js"></script>
<script src="../../../youyi/assetses/bootstrap/js/bootstrap.min.js"></script>
<script src="../../../youyi/assetses/js/jquery.backstretch.min.js"></script>
<script src="../../../youyi/assetses/js/scripts.js"></script>

<!--[if lt IE 10]>
<script src="../../../youyi/assetses/js/placeholder.js"></script>
<![endif]-->

</body>

</html>
<%--</body>--%>
<%--</html>--%>
