<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru,en">
    <head>
        <meta charset="UTF-8" />
        <title>Кабинет пользователя</title>
    </head>
    <body>
    <div style="text-align: left;">
        <h1 style="text-align: center;">Кабинет пользователя: ${person}</h1>
        <h3>Выберите необходимое действие</h3>
        <div>
            <form action="transact" method="get">
                <button type="submit">Транзакции</button>
            </form>
            <br/>
            <form action="category" method="get">
                <button type="submit">Категории</button>
            </form>
            <br/>
            <form action="budget" method="get">
                <button type="submit">Бюджет</button>
            </form>
            <br/>
            <form action="financial-goal" method="get">
                <button type="submit">Долгосрочные финансовые цели</button>
            </form>
            <br/>
            <form action="family" method="get">
                <button type="submit">Семейная группа</button>
            </form>
            <br/>
            <form action="person" method="get">
                <button type="submit">Обновить данные пользователя</button>
            </form>
            <br/>
            <form action="person" method="post">
                <button type="submit">Удалить учетную запись пользователя</button>
            </form>
            <br/>
            <br/>
            <form action="logout" method="get">
                <button type="submit">Выйти из системы</button>
            </form>
            <br/>
        </div>
    </div>
    </body>
</html>