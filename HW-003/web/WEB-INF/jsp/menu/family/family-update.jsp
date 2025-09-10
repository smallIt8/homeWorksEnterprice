<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление семейной группы</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление имени семейной группы пользователя: <span style="color: blue;">${personName}</span></h1>
            <div>
                <c:if test="${action == 'update'}">
                    <h3>Выберите семейную группу для обновления:</h3>
                    <c:forEach var="family" items="${families}">
                        <div class="family-box">
                            <p>${family.name}</p>
                            <form action="${pageContext.request.contextPath}/family" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="update-family"/>
                                <input type="hidden" name="familyId" value="${family.familyId}"/>
                                <button type="submit">Обновить</button>
                            </form>
                        </div>
                    </c:forEach>
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
                </c:if>
            </div>

            <div>
                <c:if test="${action == 'update-family'}">
                    <h3>Обновление семейной группы: <span style="color: orange;">${family.name}</span></h3>
                    <form action="${pageContext.request.contextPath}/family" method="get">
                        <input type="hidden" name="action" value="update-family"/>
                        <input type="hidden" name="familyId" value="${family.familyId}"/>
                        <input type="hidden" name="name" value="${family.name}"/>
                    </form>

                    <form action="${pageContext.request.contextPath}/family" method="post">
                        <input type="hidden" name="action" value="updated-family"/>
                        <input type="hidden" name="familyId" value="${family.familyId}"/>
                        <div>
                            <label>Имя семейной группы:
                                <input type="text" name="name" value="${family.name}" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Сохранить изменения</button>
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