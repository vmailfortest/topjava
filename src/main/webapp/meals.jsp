<%--
  Created by IntelliJ IDEA.
  User: vs
  Date: 06.10.2019
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<section>

    <table border="1">
        <tr>
            <th style="width:45%">Дата/Время</th>
            <th style="width:35%">Описание</th>
            <th style="width:20%">Калории</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>

            <c:if test="${meal.excess == true}">
                <c:set var="fontColor" scope="request" value="green"/>
            </c:if>
            <c:if test="${meal.excess == false}">
                <c:set var="fontColor" scope="request" value="red"/>
            </c:if>

            <tr style="color:${fontColor}">
                    <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>

            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
