<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        #filter-table {
            width: 100%;
        }

        #filter-table th {
            background-color: #dadada;
        }

        #filter-table td, #filter-table th {
            padding: 5px;
            border-bottom: 1px solid #ccc;
        }
    </style>
</head>
<body>
<section>
    <h1><a href="index.html">Home</a></h1>
    <form method="post" action="meals&action=filter">
    <table id="filter-table">
        <tr>
            <th>От даты (включая)</th>
            <th>До даты (включая)</th>
            <th>От времени (включая)</th>
            <th>До времени (включая)</th>
        </tr>
        <tr class='table-filters'>
            <td>
                <input type="date" value=${param.startDate} name="startDate"/>
            </td>
            <td>
                <input type="date" value=${param.finishDate} name="finishDate"/>
            </td>
            <td>
                <input type="time" value=${param.startTime} name="startTime"/>
            </td>
            <td>
                <input type="time" value=${param.finishTime} name="finishTime"/>
            </td>
        </tr>
    </table>
    <button type="submit">Фильтр</button>
    </form>
    <%--<form method="post" action="filteredMeals">
        <dl>
            <dt>От даты (включая):</dt>
            <dd><input type="date" value="" name="firstDateTime" required></dd>
        </dl>
        <dl>
            <dt>До даты (включая):</dt>
            <dd><input type="date" value="" name="secondDateTime" required></dd>
        </dl>
        <dl>
            <dt>От времени (включая):</dt>
            <dd><input type="time" value="" name="firstDateTime" required></dd>
        </dl>
        <dl>
            <dt>До времени (включая):</dt>
            <dd><input type="time" value="" name="firstDateTime" required></dd>
        </dl>

        <button type="submit">Фильтр</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
--%>
    <h2></h2>
    <hr/>
    <h3>Meals</h3>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>