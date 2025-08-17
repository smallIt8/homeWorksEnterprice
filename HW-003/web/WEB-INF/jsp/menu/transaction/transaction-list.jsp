<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Список транзакций</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Список транзакций пользователя ${personName}:</h1>
            <div>
                <c:if test="${action == 'list'}">

                </c:if>
            </div>
        </div>
    </body>
</html>