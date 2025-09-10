<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление категории</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление имени категории пользователя: <span style="color: blue;">${personName}</span></h1>
            <div>
                <c:if test="${action == 'update'}">
                    <h3>Выберите категорию для обновления:</h3>
                    <c:forEach var="category" items="${categories}">
                        <div class="category-box">
                            <p>${category.name} | ${category.type.typeName}</p>
                            <form action="${pageContext.request.contextPath}/category" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="update-category"/>
                                <input type="hidden" name="categoryId" value="${category.categoryId}"/>
                                <button type="submit">Обновить</button>
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
                </c:if>
            </div>

            <div>
                <c:if test="${action == 'update-category'}">
                    <h3>Обновление категории: <span style="color: orange;">${category.name}</span></h3>
                    <form action="${pageContext.request.contextPath}/category" method="get">
                        <input type="hidden" name="action" value="update-category"/>
                        <input type="hidden" name="categoryId" value="${category.categoryId}"/>
                        <input type="hidden" name="name" value="${category.name}"/>
                    </form>

                    <form action="${pageContext.request.contextPath}/category" method="post">
                        <input type="hidden" name="action" value="updated-category"/>
                        <input type="hidden" name="categoryId" value="${category.categoryId}"/>
                        <div>
                            <label>Имя категории:
                                <input type="text" name="name" value="${category.name}" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Сохранить изменения</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/category" method="get">
                        <button type="submit">Отмена</button>
                    </form>
                </c:if>
            </div>
        </div>
    </body>
</html>