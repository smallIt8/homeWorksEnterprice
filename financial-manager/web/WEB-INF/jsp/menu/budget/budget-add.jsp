<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Добавление бюджета</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Установить бюджет на категорию:</h1>
            <div>
                <c:if test="${action == 'add'}">
                    <form action="${pageContext.request.contextPath}/budget" method="post">
                        <input type="hidden" name="action" value="add-budget"/>
                        <div>
                            <label for="name">Имя бюджета:
                                <input type="text" name="name" id="name" value="${budget.budgetName}" required/>
                            </label>
                            <c:if test="${not empty warn['budgetName']}">
                                <c:forEach var="mesage" items="${warn['budgetName']}">
                                    <span style="color: red;">${mesage}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <c:choose>
                                <c:when test="${not empty categories}">
                                    <label for="category">Категория:
                                        <select name="categoryId" id="category" required>
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.categoryId}">${category.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                    <c:if test="${not empty warn['categoryDto']}">
                                        <c:forEach var="message" items="${warn['categoryDto']}">
                                            <span style="color: red;">${message}</span><br/>
                                        </c:forEach>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <div style="color: red;">${warningMessage}</div>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/category?action=add&backTo=budget">
                                <button type="button">Создать категорию</button>
                            </a>
                        </div>
                        <br/>
                        <div>
                            <label for="limit">Лимит:
                                <input type= "number" name="limit" id="limit" step="0.01" min="0.01" value="${budget.limit}" required/>
                            </label>
                            <c:if test="${not empty warn['limit']}">
                                <c:forEach var="message" items="${warn['limit']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label for="period">Период:
                                <input type= "month" name="period" id="period" value="${budget.period}" required/>
                            </label>
                            <c:if test="${not empty warn['period']}">
                                <c:forEach var="message" items="${warn['period']}">
                                    <span style="color: red;">${message}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <button type="submit">Установить</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/budget" method="get">
                        <button type="submit">Отмена</button>
                    </form>
                </c:if>
            </div>
        </div>
    </body>
</html>