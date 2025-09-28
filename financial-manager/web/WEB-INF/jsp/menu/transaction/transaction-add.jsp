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
                                <input type="text" name="name" id="name" value="${transaction.transactionName}" required/>
                            </label>
                            <c:if test="${not empty warn['transactionName']}">
                                <c:forEach var="msg" items="${warn['transactionName']}">
                                    <span style="color:red;">${msg}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label>Тип транзакции:
                                <select name="type" required onchange="location.href='${pageContext.request.contextPath}/transact?action=add&type=' + this.options[this.selectedIndex].value">
                                    <option value="EXPENSE" <c:if test="${type == 'EXPENSE'}">selected</c:if>>Расходная</option>
                                    <option value="INCOME" <c:if test="${type == 'INCOME'}">selected</c:if>>Приходная</option>
                                </select>
                            </label>
                            <c:if test="${not empty warn['type']}">
                                <c:forEach var="msg" items="${warn['type']}">
                                    <span style="color:red;">${msg}</span><br/>
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
                                        <c:forEach var="msg" items="${warn['categoryDto']}">
                                            <span style="color:red;">${msg}</span><br/>
                                        </c:forEach>
                                    </c:if>
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
                                <input type="number" name="amount" id="amount" step="0.01" min="0.01" value="${transaction.amount}" required/>
                            </label>
                            <c:if test="${not empty warn['amount']}">
                                <c:forEach var="msg" items="${warn['amount']}">
                                    <span style="color:red;">${msg}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <div>
                            <label for="transactionDate">Дата транзакции:
                                <input type="date" name="transactionDate" id="transactionDate" required/>
                            </label>
                            <c:if test="${not empty warn['transactionDate']}">
                                <c:forEach var="msg" items="${warn['transactionDate']}">
                                    <span style="color:red;">${msg}</span><br/>
                                </c:forEach>
                            </c:if>
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