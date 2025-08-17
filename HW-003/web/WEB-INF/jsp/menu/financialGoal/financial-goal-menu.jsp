<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Меню финансовых целей</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Меню долгосрочных финансовых целей пользователя: ${personName}</h1>
            <h3>Выберите необходимое действие:</h3>
            <div>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <input type="hidden" name="action" value="add"/>
                    <button type="submit">Создать долгосрочную финансовую цель</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <input type="hidden" name="action" value="update"/>
                    <button type="submit">Обновить долгосрочную финансовую цель</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <input type="hidden" name="action" value="list"/>
                    <button type="submit">Просмотреть список долгосрочных финансовых целей</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/financial-goal" method="get">
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit">Удалить долгосрочную финансовую цель</button>
                </form>
                <br/>
                <br/>
                <form action="${pageContext.request.contextPath}/main-person" method="get">
                    <button type="submit">Вернуться в главное меню</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/logout" method="get">
                    <button type="submit">Выйти из системы</button>
                </form>
                <br/>
            </div>
        </div>
    </body>
</html>