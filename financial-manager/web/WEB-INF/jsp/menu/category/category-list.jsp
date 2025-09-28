<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Список категорий</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Список категорий пользователя <span style="color: blue;">${personName}</span>:</h1>
            <div>
                <c:forEach var="category" items="${categories}">
                    <div class="category-box">
                        <p>${category.categoryName} | ${category.type.typeName}</p>
                        <form action="${pageContext.request.contextPath}/category" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="update-category"/>
                            <input type="hidden" name="categoryId" value="${category.categoryId}"/>
                            <button type="submit">Обновить</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/category" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="delete-category"/>
                            <input type="hidden" name="categoryId" value="${category.categoryId}"/>
                            <button type="submit" style="background-color:red; color:white;">Удалить</button>
                        </form>
                    </div>
                </c:forEach>
                <br/>
                <br/>
                <form action="${pageContext.request.contextPath}/category" method="get">
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
            </div>
        </div>
    </body>
</html>