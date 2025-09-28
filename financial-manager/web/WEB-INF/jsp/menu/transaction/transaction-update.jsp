<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление транзакции</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление данных транзакции пользователя: <span style="color: blue;">${personName}</span></h1>
            <div>
                <c:if test="${action == 'update'}">
                    <h3>Выберите транзакцию для обновления:</h3>
                    <c:forEach var="transact" items="${transactions}">
                        <div class="transaction-box">
                            <p>${transact.transactionName} | ${transact.type.typeName} | ${transact.categoryDto.categoryName} | ${transact.amount} | ${transact.transactionDate}</p>
                            <form action="${pageContext.request.contextPath}/transact" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="update-transact"/>
                                <input type="hidden" name="transactionId" value="${transact.transactionId}"/>
                                <button type="submit">Обновить</button>
                            </form>
                        </div>
                    </c:forEach>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/transact" method="get">
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
                <c:if test="${action == 'update-transact'}">
                    <c:set var="transaction" value="${not empty transaction ? transaction : sessionScope.currentTransaction}" />
                    <h3>Обновление транзакции: <span style="color: orange;">${transaction.transactionName}</span></h3>
                    <form action="${pageContext.request.contextPath}/transact" method="post">
                        <input type="hidden" name="action" value="updated-transact"/>
                        <input type="hidden" name="transactionId" value="${transaction.transactionId}"/>
                        <div>
                            <label>Имя транзакции:
                                <input type="text" name="name" value="${transaction.transactionName}" required/>
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
                                <select name="type" onchange="location.href='${pageContext.request.contextPath}/transact?action=update-transact&transactionId=${transaction.transactionId}&type=' + this.options[this.selectedIndex].value">
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
                                                <option value="${category.categoryId}" <c:if test="${category.categoryId == transaction.categoryDto.categoryId}">selected</c:if>>${category.categoryName}</option>
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
                            <label>Стоимость:
                                <input type="number" name="amount" value="${transaction.amount}" step="0.01" min="0.01" required/>
                            </label>
                            <c:if test="${not empty warn['amount']}">
                                <c:forEach var="msg" items="${warn['amount']}">
                                    <span style="color:red;">${msg}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div>
                            <label>Дата транзакции:
                                <input type="date" name="transactionDate" value="${transaction.transactionDate}" required/>
                            </label>
                            <c:if test="${not empty warn['transactionDate']}">
                                <c:forEach var="msg" items="${warn['transactionDate']}">
                                    <span style="color:red;">${msg}</span><br/>
                                </c:forEach>
                            </c:if>
                        </div>
                        <br/>
                        <button type="submit">Сохранить изменения</button>
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