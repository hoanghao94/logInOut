<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        form {border: 3px solid #f1f1f1;}

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #04AA6D;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 130px;
            height: 45px;
        }

        button:hover {
            opacity: 0.8;
        }

        .cancelbtn {
            padding: 10px 18px;
            background-color: #f44336;
            width: 130px;
            height: 45px;
        }

        .imgcontainer {
            text-align: center;
            margin: 24px 0 12px 0;
        }

        img.avatar {
            width: 40%;
            border-radius: 50%;
        }

        .container {
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }
        .login_Form {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .inline{
            display: flex;
            align-items: center;
        }

        /* Hide the error message element by default */
        .error-message {
            display: none;
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) {
            span.psw {
                display: block;
                float: none;
            }
            .cancelbtn {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<div class="login_Form">
    <h2>Login Form</h2>
</div>

<form method="post">

    <div class="container">
        <div class= "inline">
            <label for="userName"><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="userName" required>
        </div>

        <div class= "inline">
            <label for="password"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="password" required>
        </div>

        <div class="error-message">
            <c:if test="${not empty errorMessage}">
                ${errorMessage}
            </c:if>
        </div>

        <div class= "inline">
            <button type="submit">Login</button>
            <button type="button" class="cancelbtn">Cancel</button>
        </div>
    </div>

    <div class="container" style="background-color:#f1f1f1">
    </div>

</form>

</body>
</html>
