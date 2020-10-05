<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang ="ru">
<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    tr[red]{
        color:red;
    }
    tr[green]{
        color:green;
    }

</style>
<% request.setAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm"));%>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a> </h3>
<hr>
<h2>Meals</h2>
<p><a href = "meals?action=insert">Add Meal</a></p>
<p></p>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items = "${mealList}" var="meal">
            <tr ${meal.excess? 'red':'green'}>
                <td>${meal.dateTime.format(formatter)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=edit&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
