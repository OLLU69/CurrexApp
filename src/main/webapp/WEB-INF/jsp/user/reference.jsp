<%--
  Created by IntelliJ IDEA.
  User: oleg
  Date: 20.05.19
  Time: 19:23
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common.jsp" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Справочник</title>
    <%@include file="../js.jsp" %>
</head>
<body>
<h2>Справочник</h2>
<p></p>
<input type="button" value="Обмен" onclick="toExchange()">
<input type="button" value="Операции" onclick="toUserOperations()">
<input type="button" value="Выход" onclick="toLogout()">
<p></p>
<%--suppress HtmlDeprecatedAttribute --%>
<table border="1" cellspacing="0" cellpadding="5">
    <thead>
    <tr>
        <td>числовой код</td>
        <td>символьный код</td>
        <td>название</td>
        <td>курс НБУ</td>
        <td>курс покупки</td>
        <td>курс продажи</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ref" items="${references}">
        <tr id="${ref.id}">
            <td>${ref.numberCode}</td>
            <td>${ref.stringCode}</td>
            <td>${ref.name}</td>
            <td>${ref.rate}</td>
            <td>${ref.saleRate}</td>
            <td>${ref.buyRate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
