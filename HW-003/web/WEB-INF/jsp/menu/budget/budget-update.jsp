<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление данных бюджета</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление данных бюджета пользователя: <span style="color: blue;">${personName}</span></h1>
            <div>
                <c:if test="${action == 'update'}">
                    <h3>Выберите бюджет для обновления:</h3>
                    <c:forEach var="budget" items="${budgets}">
                        <div class="budget-box">
                            <p>${budget.name} | ${budget.categoryDto.name} | ${budget.limit} | ${budget.period}</p>
                            <form action="${pageContext.request.contextPath}/budget" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="update-budget"/>
                                <input type="hidden" name="budgetId" value="${budget.budgetId}"/>
                                <button type="submit">Обновить</button>
                            </form>
                        </div>
                    </c:forEach>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/budget" method="get">
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
                <c:if test="${action == 'update-budget'}">
                    <h3>Обновление бюджета: <span style="color: orange;">${budget.name}</span></h3>
                    <form action="${pageContext.request.contextPath}/budget" method="get">
                        <input type="hidden" name="action" value="update-budget"/>
                        <input type="hidden" name="budgetId" value="${budget.budgetId}"/>
                        <input type="hidden" name="name" value="${budget.name}"/>
                        <input type="hidden" name="limit" value="${budget.limit}"/>
                        <input type="hidden" name="period" value="${budget.period}"/>
                    </form>

                    <form action="${pageContext.request.contextPath}/budget" method="post">
                        <input type="hidden" name="action" value="updated-budget"/>
                        <input type="hidden" name="budgetId" value="${budget.budgetId}"/>
                        <div>
                            <label>Имя бюджета:
                                <input type="text" name="name" value="${budget.name}" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <c:choose>
                                <c:when test="${not empty categories}">
                                    <label for="category">Категория:
                                        <select name="categoryId" id="category">
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.categoryId}" <c:if test="${category.categoryId == budget.categoryDto.categoryId}">selected</c:if>>${category.name}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
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
                            <label>Лимит:
                                <input type="number" name="limit" value="${budget.limit}" step="0.01" min="0.01" required/>
                            </label>
                        </div>
                        <div>
                            <label>Период бюджета:
                                <input type="month" name="period" value="${budget.period}" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Сохранить изменения</button>
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