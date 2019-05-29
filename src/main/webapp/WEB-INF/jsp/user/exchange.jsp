<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common.jsp" %>
<jsp:useBean id="references" scope="request" type="java.util.List<ua.dp.ollu.currex.currex_app.model.Reference>"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Пользователь</title>
    <%@include file="../js.jsp" %>
    <script type="text/javascript">
        var references = [
                <c:forEach var="ref" items="${references}">{
                id:${ref.id},
                stringCode: "${ref.stringCode}",
                name: "${ref.name}",
                rate: ${ref.rate},
                saleRate: ${ref.saleRate},
                buyRate: ${ref.buyRate}
            }, </c:forEach>
        ];

        var rate = 0.0;
        var currIndex = 0;

        $(function () {
            rate = references[currIndex].saleRate;
            setRateLabel();
        });

        function setRateLabel() {
            $("label#rate").text(rate)
        }

        function setBuyType() {
            var ref = references[currIndex];
            rate = ref.saleRate;
            setRateLabel();
            onCurrInChange()
        }

        function setSaleType() {
            var ref = references[currIndex];
            rate = ref.buyRate;
            setRateLabel();
            onCurrInChange()
        }

        function onBuyType(radio) {
            if (radio.value === "0") setBuyType();
            if (radio.value === "1") setSaleType();
        }

        function getTypeRadio() {
            return $("input[type=radio][name=type]:checked");
        }

        function onChangeCurrency(select) {
            currIndex = $(select).val();
            onBuyType(getTypeRadio()[0])
        }


        function getInSumm() {
            return $("#currSumm").val();
        }

        function getOutSumm() {
            return $("#result").val();
        }

        function onCurrInChange() {
            console.log("onCurrInChange");
            //пересчитать результат
            var rr = getInSumm() * rate;
            $("#result").val(rr)
        }

        function onCurrOutChange() {
            console.log("onCurrOutChange");
            //пересчитать результат
            var rr = getOutSumm() / rate;
            $("#currSumm").val(rr)
        }

        function onExchange() {
            console.log("onExchange");
            //если режим покупка то #currSumm - сумма покупки
            var data = {
                buySumm: $("#currSumm").val(),
                saleSumm: $("#result").val(),
                currencyBuy: references[currIndex].stringCode,
                currencySale: "UAH",
                exchangeRate: rate,
                swap: function () {
                    var tmp = this.buySumm;
                    this.buySumm = this.saleSumm;
                    this.saleSumm = tmp;
                    tmp = this.currencyBuy;
                    this.currencyBuy = this.currencySale;
                    this.currencySale = tmp;
                }
            };
            //<%--suppress EqualityComparisonWithCoercionJS --%>
            if (getTypeRadio().val() == 1) {
                //если режим продажа то #result - сумма покупки
                data.swap()
            }

            exchange(data, function () {
                alert("Операция завершена успешно");
                $("#currSumm").val("0.00");
                $("#result").val("0.00");
            }, function (error) {
                alert("Операция завершена с ошибкой:" + error)
            });
        }
    </script>
</head>
<body>
<h2>Обмен</h2>
<p></p>
<input type="button" onclick="toHome()" value="Домой">
<input value="операции" type="button" onclick="toUserOperations()">
<input value="справочник" type="button" onclick="toUserReference()">
<p></p>
<input type="button" onclick="onExchange()" value="обмен">
<p></p>
<table>
    <tr>
        <td>
            <label for="currencySelector">Валюта</label>
        </td>
        <td>
            <select id="currencySelector" onchange="onChangeCurrency(this)">
                <c:forEach var="ref" items="${references}" varStatus="loop">
                    <option value="${loop.index}">${ref.stringCode}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td><label>Курс</label></td>
        <td><label id="rate"></label></td>
    </tr>
    <tr>
        <%--suppress HtmlFormInputWithoutLabel --%>
        <td>
            <%--suppress HtmlFormInputWithoutLabel --%>
            <input type="radio" name="type" checked value="0" onclick="onBuyType(this)">Покупка
        </td>
        <td>
            <%--suppress HtmlFormInputWithoutLabel --%>
            <input type="radio" name="type" value="1" onclick="onBuyType(this)">Продажа
        </td>
    </tr>
    <tr>
        <td><label for="currSumm">Сумма</label></td>
        <td><input id="currSumm" onkeyup="onCurrInChange()" type="number" value="0.00"></td>
    </tr>
    <tr>
        <td><label for="result">Результат грн</label></td>
        <td><input id="result" onkeyup="onCurrOutChange()" type="number" value="0.00"></td>
    </tr>
</table>
</body>
</html>
