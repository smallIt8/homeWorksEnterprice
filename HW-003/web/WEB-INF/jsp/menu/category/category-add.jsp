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
                        <input type="hidden" name="action" value="add"/>
                        <div>
                            <label for="categoryName">Имя категории:
                                <input type="text" name="categoryName" id="categoryName" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label for="type">Тип категории:
                                <input type= "text" name="type" id="type" required/>
                            </label>
                        </div>
                        <br/>
                        <br/>
                        <button type="submit">Создать</button>
                    </form>
                    <br/>
                </c:if>
            </div>
        </div>
    </body>
</html>