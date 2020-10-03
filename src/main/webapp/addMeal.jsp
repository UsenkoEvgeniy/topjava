<%--
  Created by IntelliJ IDEA.
  User: usenk
  Date: 03.10.2020
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang ="ru">
<head>
    <title>Add or Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a> </h3>
<hr>
<h2>Edit Meal</h2>
<form action="meals" method="post">
    <input type="hidden" name="id" value="<c:out value="${meal.id}"/>" />
    Date: <input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>" /> <br />
    Description: <input type="text" name="description" value="<c:out value="${meal.description}" />" /> <br />
    Calories: <input type="text" name="calories" pattern="\d+" value="<c:out value="${meal.calories}" />" /> <br/>
    <br/>
    <input type="submit" value="Submit" />
</form>
</body>
</html>
