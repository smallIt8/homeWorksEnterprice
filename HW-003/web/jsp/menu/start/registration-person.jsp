<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru,en">
<head>
    <meta charset="UTF-8" />
    <title>Авторизация</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Вход в личный кабинет</h1>
    <form action="start" method="post">
        <input type="hidden" name="action" value="register"/>
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
        <div>
            <label>
                Имя:
                <input type="text" name="firstName" />
            </label>
        </div>
        <br/>
        <div>
            <label>
                Фамилия:
                <input type="text" name="lastName" />
            </label>
        </div>
        <br/>
        <div>
            <label>
                Эмейл:
                <input type="email" name="email" />
            </label>
        </div>
        <br/>
        <button type="submit">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>