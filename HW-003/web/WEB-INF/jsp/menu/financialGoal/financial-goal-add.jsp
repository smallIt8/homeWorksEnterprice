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
                                <input type="text" name="name" id="name" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="targetAmount">Цель:
                                <input type="number" name="targetAmount" id="targetAmount" step="0.01" min="0.01" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="endDate">Конечная дата накопления:
                                <input type="date" name="endDate" id="endDate" required/>
                            </label>
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