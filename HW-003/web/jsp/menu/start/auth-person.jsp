<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru,en">
<head>
    <meta charset="UTF-8" />
    <title>Регистрация</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Регистрация нового пользователя</h1>
    <form action="start" method="post">
        <input type="hidden" name="action" value="auth"/>
        <div>
            <label>
                Логин:
                <input type="text" name="userName" />
            </label>
        </div>
        <br/>
        <div>
            <label>
                Пароль:
                <input type="password" name="password" />
            </label>
        </div>
        <br/>
        <button type="submit">Войти</button>
    </form>
</div>
</body>
</html>