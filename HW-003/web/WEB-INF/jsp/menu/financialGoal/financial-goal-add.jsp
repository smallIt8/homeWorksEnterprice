<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
<head>
    <title>Создание финансовой цели</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Создание долгосрочной финансовой цели:</h1>
            <div>
                <c:if test="${action == 'add'}">
                    <form action="${pageContext.request.contextPath}/financial-goal" method="post">
                        <input type="hidden" name="action" value="add-financial-goal"/>
                        <div>
                            <label for="name">Имя долгосрочной финансовой цели:
                                <input type="text" name="name" id="name" value="${financialGoal.financialGoalName}" required/>
                            </label>
                            <c:if test="${not empty warn['financialGoalName']}">
                                <c:forEach var="message" items="${warn['financialGoalName']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label for="targetAmount">Цель:
                                <input type="number" name="targetAmount" id="targetAmount"  value="${financialGoal.targetAmount}" required/>
                            </label>
                            <c:if test="${not empty warn['targetAmount']}">
                                <c:forEach var="message" items="${warn['targetAmount']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label for="endDate">Конечная дата накопления:
                                <input type="date" name="endDate" id="endDate" value="${financialGoal.endDate}" required/>
                            </label>
                            <c:if test="${not empty warn['endDate']}">
                                <c:forEach var="message" items="${warn['endDate']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <button type="submit">Создать</button>
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