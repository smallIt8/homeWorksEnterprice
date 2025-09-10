<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Добавление транзакции</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Добавление новой транзакции:</h1>
            <div>
                <c:if test="${action == 'add'}">
                    <form action="${pageContext.request.contextPath}/transact" method="post">
                        <input type="hidden" name="action" value="add-transact"/>
                        <div>
                            <label for="name">Имя транзакции:
                                <input type="text" name="name" id="name" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label>Тип транзакции:
                                <select name="type" required onchange="location.href='${pageContext.request.contextPath}/transact?action=add&type=' + this.options[this.selectedIndex].value">
                                    <option value="EXPENSE" <c:if test="${type == 'EXPENSE'}">selected</c:if>>Расходная</option>
                                    <option value="INCOME" <c:if test="${type == 'INCOME'}">selected</c:if>>Приходная</option>
                                </select>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <c:choose>
                                <c:when test="${not empty categories}">
                                    <label for="category">Категория:
                                        <select name="categoryId" id="category">
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.categoryId}">${category.name}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                </c:when>
                                <c:otherwise>
                                    <div style="color: red;">${warningMessage}</div>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/category?action=add&backTo=transact">
                                <button type="button">Создать категорию</button>
                            </a>
                        </div>
                        <br/>
                        <div>
                            <label for="amount">Стоимость:
                                <input type="number" name="amount" id="amount" step="0.01" min="0.01" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="transactionDate">Дата транзакции:
                                <input type="date" name="transactionDate" id="transactionDate" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Добавить</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/transact" method="get">
                        <button type="submit">Отмена</button>
                    </form>
                </c:if>
            </div>
        </div>
    </body>
</html>