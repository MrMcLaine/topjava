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
<form method="post" action="users">
    <label>
        <select name="userId">
            <option value="1">User</option>
            <option value="2">Admin</option>
        </select>
    </label>
    <button type="submit">Select</button>
</form>
<section>
    <h1><a href="index.html">Home</a></h1>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
    <table id="filter-table">
        <tr>
            <th>От даты (включая)</th>
            <th>До даты (включая)</th>
            <th>От времени (включая)</th>
            <th>До времени (включая)</th>
        </tr>
        <tr class='table-filters'>
            <td>
                <label>
                    <input type="date" value=${param.startDate} name="startDate"/>
                </label>
            </td>
            <td>
                <label>
                    <input type="date" value=${param.finishDate} name="finishDate"/>
                </label>
            </td>
            <td>
                <label>
                    <input type="time" value=${param.startTime} name="startTime"/>
                </label>
            </td>
            <td>
                <label>
                    <input type="time" value=${param.finishTime} name="finishTime"/>
                </label>
            </td>
        </tr>
    </table>
    <button type="submit">Фильтр</button>
    </form>
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