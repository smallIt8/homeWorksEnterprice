<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Удаление бюджета</title>
        <style>
            .warningMessage {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div style="text-align: center;">
            <h1>Удаление выбранного бюджета: ${budgetName}</h1>
            <c:if test="${not empty warningMessage}">
                <p class="warningMessage">${warningMessage}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/budget" method="post">
                <input type="hidden" name="action" value="delete-budget"/>
                <button type="submit" style="background-color: red; color: white;">Удалить категорию</button>
            </form>

            <form action="${pageContext.request.contextPath}/budget" method="get">
                <button type="submit">Отмена</button>
            </form>
        </div>
    </body>
</html>