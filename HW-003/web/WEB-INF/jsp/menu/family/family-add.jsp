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
                        <input type="hidden" name="action" value="add-family"/>
                        <c:if test="${not empty backTo}">
                            <input type="hidden" name="backTo" value="${backTo}"/>
                        </c:if>
                        <div>
                            <label for="name">Имя семейной группы:
                                <input type="text" name="name" id="name" value="${family.familyName}" required/>
                            </label>
                            <c:if test="${not empty warn['familyName']}">
                                <c:forEach var="message" items="${warn['familyName']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <button type="submit">Создать</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/family" method="get">
                        <button type="submit">Отмена</button>
                    </form>
                </c:if>
            </div>
        </div>
    </body>
</html>