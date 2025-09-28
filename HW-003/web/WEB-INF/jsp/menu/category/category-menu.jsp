<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Меню категорий</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Меню категорий пользователя: <span style="color: blue;">${personName}</span></h1>
            <h3>Выберите необходимое действие:</h3>
            <div>
                <form action="${pageContext.request.contextPath}/category" method="get">
                    <input type="hidden" name="action" value="add"/>
                    <button type="submit">Создать категорию</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/category" method="get">
                    <input type="hidden" name="action" value="update"/>
                    <button type="submit">Обновить имя категории</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/category" method="get">
                    <input type="hidden" name="action" value="list"/>
                    <button type="submit">Просмотреть список категорий</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/category" method="get">
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit">Удалить категорию</button>
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
            </div>
        </div>
    </body>
</html>