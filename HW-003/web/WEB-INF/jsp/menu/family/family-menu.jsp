<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Меню семейной группы</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Меню семейной группы пользователя: <span style="color: blue;">${personName}</span></h1>
            <h3>Выберите необходимое действие:</h3>
            <div>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="add"/>
                    <button type="submit">Создать семейную группу</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="entry-family"/>
                    <button type="submit">Вступить в семейную группу</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="add-person-family"/>
                    <button type="submit">Добавить пользователя в семейную группу</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="update"/>
                    <button type="submit">Обновить имя семейной группы</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="list"/>
                    <button type="submit">Просмотреть список семейных групп</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="list"/>
                    <button type="submit">Просмотреть список семейных групп, в которых состоит пользователь</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="exit-family"/>
                    <button type="submit">Выйти из семейной группы</button>
                </form>
                <br/>
                <form action="${pageContext.request.contextPath}/family" method="get">
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit">Удалить семейную группу</button>
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