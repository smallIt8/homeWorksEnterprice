<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
<head>
    <title>Регистрация</title>
    </head>
    <body>
        <div style="text-align: center;">
            <h1>Регистрация нового пользователя</h1>
            <form action="start" method="post">
                <input type="hidden" name="action" value="register"/>
                <div>
                    <label for="UserName">
                        Логин:
                        <input type="text" name="userName" id="UserName"/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="password">
                        Пароль:
                        <input type="password" name="password" id="password"/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="firstName">
                        Имя:
                        <input type="text" name="firstName" id="firstName"/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="lastName">
                        Фамилия:
                        <input type="text" name="lastName" id="lastName"/>
                    </label>
                </div>
                <br/>
                <div>
                    <label for="email">
                        Эмейл:
                        <input type="email" name="email" id="email"/>
                    </label>
                </div>
                <br/>
                <button type="submit">Зарегистрироваться</button>
            </form>
        </div>
    </body>
</html>