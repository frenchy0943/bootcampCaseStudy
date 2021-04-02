<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Register</title>
    <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script src="./scripts/login.js"></script>
</head>
<body style = "background-color: #1f2833;">
    <div class = "container" style="background-color: #0b0c10; ">
        <div class = "jumbotron text-center" style ="background-color:#66fcf1">
            <h1>Register New Account</h1>
        </div>
        <form>
            <input type="text" id="email" placeholder="Username"/>
            <input type="password" id="password" placeholder="Password"/>
            <input id = "passwordCheck" placeholder="Re-enter password">
            <button type = "button" onclick="checkLogin()">Register</button>
        </form>
        <div style="color: white">
           Already have an account? Click <a href="login">here</a> to login!
        </div>
        <img src="/VideoGameTracker/resources/images/singleController.jpg" alt="" style="width: 100%">
    </div>
</body>
</html>