<%--
  Created by IntelliJ IDEA.
  User: vs
  Date: 08.10.2019
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit meal</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
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
        </tr>
        <tr>
            <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="uuid" value="${meal.uuid}">
                <td><input type="text" name="date" size=30 value="${meal.dateTime.format(formatter)}"></td>
                <td><input type="text" name="desc" size=30 value="${meal.description}"></td>
                <td><input type="text" name="cal" size=30 value="${meal.calories}"></td>
                <td><a href="meals">
                    <button type="submit">Save</button>
                </a></td>
                <td><a href="meals">
                    <button type="button">cancel</button>
                </a></td>
            </form>
        </tr>
    </table>
</section>

</body>
</html>
