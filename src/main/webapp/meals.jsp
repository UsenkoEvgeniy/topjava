<%--
  Created by IntelliJ IDEA.
  User: usenk
  Date: 02.10.2020
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang ="ru">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a> </h3>
<hr>
<h2>Meals</h2>
<table border="1" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items = "${mealList}" var="meal">
            <tr style="color:${meal.excess? ' green':' red'}">
                <td><c:out value="${meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))}" /></td>
                <td><c:out value="${meal.description}" /></td>
                <td><c:out value="${meal.calories}" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
