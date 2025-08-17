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
                        <input type="hidden" name="action" value="add"/>
                        <div>
                            <label for="transactionName">Имя транзакции:
                                <input type="text" name="transactionName" id="transactionName" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="type">Тип транзакции:
                                <input type= "text" name="type" id="type" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="category">Категория:
                                <input type="text" name="category" id="category" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="amount">Стоимость:
                                <input type="number" name="amount" id="amount" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="transactionDate">Дата транзакции:
                                <input type="datetime-local" name="transactionDate" id="transactionDate" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Добавить</button>
                    </form>
                    <br/>
                </c:if>
            </div>
        </div>
    </body>
</html>