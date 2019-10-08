<%--
  Created by IntelliJ IDEA.
  User: vs
  Date: 06.10.2019
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<c:set var="formatter" scope="application" value="${DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm\")}"/>

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
            <th style="width:200px">Дата/Время</th>
            <th style="width:200px">Описание</th>
            <th style="width:100px">Калории</th>
            <th style="width:50px"></th>
            <th style="width:50px"></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>

            <c:set var="fontColor" scope="request" value="${meal.excess ? 'red':'green'}"/>

            <tr style="color:${fontColor}">
                <td>${meal.dateTime.format(formatter)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?uuid=${meal.uuid}&action=edit">edit</a></td>
                <td><a href="meals?uuid=${meal.uuid}&action=delete">delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
                <td><input type="text" name="date" size=30 value="" required></td>
                <td><input type="text" name="desc" size=30 value="" required></td>
                <td><input type="text" name="cal" size=30 value="" required></td>
                <td><a href="meals">
                    <button type="submit">Add</button>
                </a></td>
                <td></td>
            </form>
        </tr>
    </table>
</section>

</body>
</html>
