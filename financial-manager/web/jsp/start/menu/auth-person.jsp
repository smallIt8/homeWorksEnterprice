<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Авторизация</title>
        <style>
            .errorMessage {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div style="text-align: center;">
            <h1>Вход в личный кабинет</h1>
            <c:if test="${not empty errorMessage}">
                <p class="errorMessage">${errorMessage}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/start" method="post">
                <input type="hidden" name="action" value="auth"/>
                <div>
                    <label for="userName">Логин:
                        <input type="text" name="userName" id="userName"/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="password">Пароль:
                        <input type="password" name="password" id="password"/>
                    </label>
                </div>
                <br/>
                <button type="submit">Войти</button>
            </form>
        </div>
    </body>
</html>