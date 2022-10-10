<%@ page import="java.util.Map" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--

  Created by IntelliJ IDEA.
  User: ВладимирЛиповский
  Date: 09/10/2022
  Time: 11:09 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<html>
<head>
    <title>Meals</title>
</head>
<body>
<h1>Meals meals</h1>
<p><a href='<c:url value="/create" />'>Create new</a></p>
<table border="1px">
    <caption>Meals</caption>--%>
<div id="main">
    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>

        <c:set var="i" value="1"/>

        <c:forEach items="${listMeal}" var="mt1">
            <tr style="color:${mt1.excess ? 'greenyellow' : 'red'}">
                <td>${i}</td>
                <td>${mt1.dateTime}
                </td>
                <td>${mt1.description}
                </td>
                <td>${mt1.calories}
                </td>
                <td>
                </td>
                <td>
                </td>
            </tr>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>

        </tbody>
    </table>
</div>


<%--<tr><th>Date</th><th>Description</th><th>Calories</th><th></th><th></th></tr>
<c:forEach var ="meal" items = "${listMeal}" >
<tr><td>${meal.getDateTime()}</td>
    <td>${meal.getDescription()}</td>
    <td>
    <a href='<c:url value="/edit?id=${meal.id}" />'>Edit</a> |
        <form method="post" action='<c:url value="/delete" />' style="display:inline;">
            <input type="hidden" name="id" value="${meal.id}">
            <input type="submit" value="Delete">
        </form>
    </td></tr>

&lt;%&ndash;    <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th>  </th>
    <th>  </th>
</tr>&ndash;%&gt;
</c:forEach>--%>
<%--</table>
</body>
</html>--%>
