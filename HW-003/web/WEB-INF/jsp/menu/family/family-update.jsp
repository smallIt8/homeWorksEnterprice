<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление семейной группы</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление выбранной семейной группы: ${familyName}</h1>
            <h3>Выберите необходимое действие:</h3>
            <div>
                <c:if test="${action == 'update'}">
                    <form action="${pageContext.request.contextPath}/family" method="get">
                        <input type="hidden" name="action" value="update-family"/>
                        <button type="submit">Обновить имя семейной группы</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/family" method="get">
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
                    <br/>
                </c:if>
            </div>
        </div>
    </body>
</html>