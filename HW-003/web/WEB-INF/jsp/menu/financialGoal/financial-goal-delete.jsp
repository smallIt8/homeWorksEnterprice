<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Удаление финансовой цели</title>
        <style>
            .warningMessage {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
    <div style="text-align: left;">
        <div>
            <c:if test="${action == 'delete'}">
                <h1 style="text-align: center;">Удаление данных долгосрочных финансовых целей пользователя: <span style="color: blue;">${personName}</span></h1>
                <h3>Выберите долгосрочную финансовую цель для удаления:</h3>
                <c:forEach var="financialGoal" items="${financialGoals}">
                    <div class="financialGoal-box">
                        <p>${financialGoal.name} | ${financialGoal.targetAmount} | ${financialGoal.endDate}</p>
                        <form action="${pageContext.request.contextPath}/financial-goal" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="delete-financial-goal"/>
                            <input type="hidden" name="financialGoalId" value="${financialGoal.financialGoalId}"/>
                            <button type="submit">Удалить</button>
                        </form>
                    </div>
                </c:forEach>
                <br/>
                <br/>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <input type="hidden" name="action" value="back"/>
                    <button type="submit">Вернуться назад</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/main-person" method="get">
                    <button type="submit">Вернуться в главное меню</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/logout" method="get">
                    <button type="submit">Выйти из системы</button>
                </form>
            </c:if>
        </div>

        <div style="text-align: center;">
            <c:if test="${action == 'delete-financial-goal'}">
                <h1>Удаление долгосрочной финансовой цели: <span style="color: orange;">${financialGoal.name}</span> пользователя: <span style="color: blue;">${personName}</span></h1>
                <c:if test="${not empty warningMessage}">
                    <p class="warningMessage">${warningMessage}</p>
                </c:if>
                <form action="${pageContext.request.contextPath}/financial-goal" method="post">
                    <input type="hidden" name="action" value="deleted-financial-goal"/>
                    <input type="hidden" name="financialGoalId" value="${financialGoal.financialGoalId}"/>
                    <button type="submit" style="background-color: red; color: white;">Удалить</button>
                </form>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <button type="submit">Отмена</button>
                </form>
            </c:if>
        </div>
    </div>
    </body>
</html>