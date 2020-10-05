<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<style>
    form  { display: table;      }
    p     { display: table-row;  }
    label { display: table-cell; }
    input { display: table-cell; }</style>
<head>
    <title>Add or Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<form action="meals" method="post">
    <p>
    <input type="hidden" name="id" value="${meal.id}"/>
    <label for="a">DateTime: </label><input id="a" type="datetime-local" name="dateTime" value="${meal.dateTime}"/> <br/>
    </p>
    <p>
    <label for="b">Description: </label><input id="b" type="text" name="description" value="${meal.description}"/> <br/>
    </p>
    <p>
    <label for="c">Calories: </label><input id="c" type="text" name="calories" pattern="\d+" value="${meal.calories}"/> <br/>
    <br/>
    </p>
    <input type="submit" value="Save"/> <a href="meals"><input type="button" value="Cancel"/></a>

</form>
</body>
</html>
