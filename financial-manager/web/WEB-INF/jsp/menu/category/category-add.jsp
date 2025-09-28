<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
<head>
    <title>Создание категории</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Создание новой категории:</h1>
            <div>
                <c:if test="${action == 'add'}">
                    <form action="${pageContext.request.contextPath}/category" method="post">
                        <input type="hidden" name="action" value="add-category"/>
                        <c:if test="${not empty backTo}">
                            <input type="hidden" name="backTo" value="${backTo}"/>
                        </c:if>
                        <div>
                            <label for="name">Имя категории:
                                <input type="text" name="name" id="name" value="${category.categoryName}" />
                            </label>
                            <c:if test="${not empty warn['categoryName']}">
                                <c:forEach var="message" items="${warn['categoryName']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label for="type">Тип категории:
                                <select name="type" id="type" >
                                    <option value="EXPENSE" ${category.type == 'EXPENSE' ? 'selected' : ''}>Расходная</option>
                                    <option value="INCOME" ${category.type == 'INCOME' ? 'selected' : ''}>Приходная</option>
                                </select>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Создать</button>
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