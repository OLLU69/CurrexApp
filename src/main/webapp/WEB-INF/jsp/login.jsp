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
   
<form name='f' action="login" method='POST'>       
    <table>
        <tr>
            <td>User:</td>
            <td>
                <%--suppress HtmlFormInputWithoutLabel --%>
                <input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <%--suppress HtmlFormInputWithoutLabel --%>
                <input type='password' name='password'/></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="submit"/></td>
        </tr>
    </table>
</form>
</body>
</html>
