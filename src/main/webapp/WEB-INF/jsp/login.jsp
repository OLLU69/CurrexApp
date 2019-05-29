<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
   
<form name='loginForm' action="login" method='POST'>       
    <table>
        <tr>
            <td><label for="user">Логин:</label></td>
            <td><input id="user" type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td><label for="pass">Пароль:</label></td>
            <td><input id="pass" type='password' name='password'/></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Вход"/></td>
        </tr>
    </table>
</form>
</body>
</html>
