<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Приложение обмен валют</title>
    <%@include file="js.jsp" %>
</head>
<body>
<h2>Обмен валют</h2>
<p></p>
<input onclick="toExchange()" type="button" value="пользователь">
<input onclick="toAdminOperations()" type="button" value="админ">
</body>
</html>
