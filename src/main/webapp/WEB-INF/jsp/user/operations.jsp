<%--
  Created by IntelliJ IDEA.
  User: oleg
  Date: 20.05.19
  Time: 19:43
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common.jsp" %>

<html>
<head>
    <title>Операции</title>
    <%@include file="../js.jsp" %>
</head>
<body>
<h2>Операции</h2>
<p></p>
<input type="button" value="Домой" onclick="toHome()">
<input type="button" value="Обмен" onclick="toExchange()">
<input type="button" value="Справочник" onclick="toUserReference()">
<p></p>
<%--suppress HtmlDeprecatedAttribute --%>
<table border="1" cellspacing="0" cellpadding="5">
    <thead>
    <tr>
        <td>Дата</td>
        <td>Валюта покупки</td>
        <td>Валюта продажи</td>
        <td>Сумма покупки</td>
        <td>Сумма продажи</td>
        <td>курс</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="op" items="${operations}">
        <tr id="${op.id}">
            <td>${formatter.format(op.date)}</td>
            <td>${op.currencyBuy}</td>
            <td>${op.currencySale}</td>
            <td>${op.buySumm}</td>
            <td>${op.saleSumm}</td>
            <td>${op.exchangeRate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
