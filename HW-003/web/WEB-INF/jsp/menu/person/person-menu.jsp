<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Кабинет пользователя</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Кабинет пользователя: ${personName}</h1>
            <h3>Выберите необходимое действие:</h3>
            <div>
                <form action="${pageContext.request.contextPath}/transact" method="get">
                    <button type="submit">Транзакции</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/category" method="get">
                    <button type="submit">Категории</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/budget" method="get">
                    <button type="submit">Бюджет</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <button type="submit">Долгосрочные финансовые цели</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <button type="submit">Семейная группа</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/main-person" method="get">
                    <input type="hidden" name="action" value="update"/>
                    <button type="submit">Обновить пользователя</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/main-person" method="get">
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit">Удалить учетную запись пользователя</button>
                </form>
                <br/>
                <br/>
                <form action="${pageContext.request.contextPath}/logout" method="get">
                    <button type="submit">Выйти из системы</button>
                </form>
                <br/>
            </div>
        </div>
    </body>
</html>