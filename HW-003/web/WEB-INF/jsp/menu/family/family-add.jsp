<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Создание семейной группы</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Создание новой семейной группы:</h1>
            <div>
                <c:if test="${action == 'add'}">
                    <form action="${pageContext.request.contextPath}/family" method="post">
                        <input type="hidden" name="action" value="add"/>
                        <div>
                            <label for="familyName">Имя транзакции:
                                <input type="text" name="familyName" id="familyName" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Добавить</button>
                    </form>
                    <br/>
                </c:if>
            </div>
        </div>
    </body>
</html>