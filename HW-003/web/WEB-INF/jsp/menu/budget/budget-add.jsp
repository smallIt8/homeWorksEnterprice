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
                        <input type="hidden" name="action" value="add"/>
                        <div>
                            <label for="budgetName">Имя бюджета:
                                <input type="text" name="budgetName" id="budgetName" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="categoryName">Категория:
                                <input type= "text" name="categoryName" id="categoryName" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="limit">Лимит:
                                <input type= "number" name="limit" id="limit" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="period">Период:
                                <input type= "date" name="period" id="period" required/>
                            </label>
                        </div>
                        <br/>
                        <br/>
                        <button type="submit">Установить</button>
                    </form>
                    <br/>
                </c:if>
            </div>
        </div>
    </body>
</html>