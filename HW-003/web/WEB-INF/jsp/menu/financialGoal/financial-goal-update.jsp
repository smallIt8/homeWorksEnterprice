<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление финансовых целей</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление долгосрочной финансовой цели: <span style="color: blue;">${personName}</span></h1>
            <div>
                <c:if test="${action == 'update'}">
                    <h3>Выберите долгосрочную финансовую цель для обновления:</h3>
                    <c:forEach var="financialGoal" items="${financialGoals}">
                        <div class="financialGoal-box">
                            <p>${financialGoal.financialGoalName} | ${financialGoal.targetAmount} | ${financialGoal.endDate}</p>
                            <form action="${pageContext.request.contextPath}/financial-goal" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="update-financial-goal"/>
                                <input type="hidden" name="financialGoalId" value="${financialGoal.financialGoalId}"/>
                                <button type="submit">Обновить</button>
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

            <div>
                <c:if test="${action == 'update-financial-goal'}">
                    <h3>Обновление долгосрочной финансовой цели: <span style="color: orange;">${financialGoal.financialGoalName}</span></h3>
                    <form action="${pageContext.request.contextPath}/financial-goal" method="post">
                        <input type="hidden" name="action" value="updated-financial-goal"/>
                        <input type="hidden" name="financialGoalId" value="${financialGoal.financialGoalId}"/>
                        <div>
                            <label>Имя долгосрочной финансовой цели:
                                <input type="text" name="name" value="${financialGoal.financialGoalName}" required/>
                            </label>
                            <c:if test="${not empty warn['financialGoalName']}">
                                <c:forEach var="message" items="${warn['financialGoalName']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label>Цель:
                                <input type="number" name="targetAmount" value="${financialGoal.targetAmount}" step="0.01" min="0.01" required/>
                            </label>
                            <c:if test="${not empty warn['targetAmount']}">
                                <c:forEach var="message" items="${warn['targetAmount']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div>
                            <label>Конечная дата накопления:
                                <input type="date" name="endDate" value="${financialGoal.endDate}" required/>
                            </label>
                            <c:if test="${not empty warn['endDate']}">
                                <c:forEach var="message" items="${warn['endDate']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <button type="submit">Сохранить изменения</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                        <button type="submit">Отмена</button>
                    </form>
                </c:if>
            </div>
        </div>
    </body>
</html>