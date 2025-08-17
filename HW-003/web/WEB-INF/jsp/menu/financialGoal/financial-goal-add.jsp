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
                        <input type="hidden" name="action" value="add"/>
                        <div>
                            <label for="financialGoalName">Имя долгосрочной финансовой цели:
                                <input type="text" name="financialGoalName" id="financialGoalName" required/>
                            </label>
                        </div>
                        <div>
                            <label for="target">Цель:
                                <input type="number" name="target" id="target" required/>
                            </label>
                        </div>
                        <div>
                            <label for="endDate">Конечная дата накопления:
                                <input type="date" name="endDate" id="endDate" required/>
                            </label>
                        </div>
                        <br/>
                        <br/>
                        <button type="submit">Создать</button>
                    </form>
                    <br/>
                </c:if>
            </div>
        </div>
    </body>
</html>