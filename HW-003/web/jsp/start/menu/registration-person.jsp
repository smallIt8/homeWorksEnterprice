<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Регистрация</title>
    </head>
    <body>
        <div style="text-align: center;">
            <h1>Регистрация нового пользователя</h1>
            <form action="${pageContext.request.contextPath}/start" method="post">
                <input type="hidden" name="action" value="register"/>
                <div>
                    <label for="userName">Логин:
                        <input type="text" name="userName" id="userName" required/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="password">Пароль:
                        <input type="password" name="password" id="password" required/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="firstName">Имя:
                        <input type="text" name="firstName" id="firstName" required/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="lastName">Фамилия:
                        <input type="text" name="lastName" id="lastName" required/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="email">Email:
                        <input type="email" name="email" id="email" required/>
                    </label>
                </div>
                <br/>
                <button type="submit">Зарегистрироваться</button>
            </form>
        </div>
    </body>
</html>