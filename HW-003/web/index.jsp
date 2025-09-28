<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Финансовый Менеджер</title>
    </head>
    <body>
        <div style="text-align: center;">
            <h1>Hello GLd-JD2-17-25!</h1>
            <h3>Выберите необходимое действие</h3>
            <div>
                <form action="${pageContext.request.contextPath}/start" method="get" style="display: inline; margin-right: 10px;">
                    <input type="hidden" name="action" value="auth"/>
                    <button type="submit">Вход</button>
                </form>

                <form action="${pageContext.request.contextPath}/start" method="get" style="display: inline;">
                    <input type="hidden" name="action" value="register"/>
                    <button type="submit">Регистрация</button>
                </form>
            </div>
        </div>
    </body>
</html>