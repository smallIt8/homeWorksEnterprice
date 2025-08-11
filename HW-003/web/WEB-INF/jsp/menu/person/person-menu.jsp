<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Кабинет пользователя</title>
    </head>
    <body>
    <div style="text-align: left;">
        <h1 style="text-align: center;">Кабинет пользователя: ${personName}</h1>
        <h3>Выберите необходимое действие</h3>
        <div>
            <form action="transact" method="get">
                <input type="hidden" name="action" value="transact"/>
                <button type="submit">Транзакции</button>
            </form>
            <br/>
            <form action="category" method="get">
                <input type="hidden" name="action" value="category"/>
                <button type="submit">Категории</button>
            </form>
            <br/>
            <form action="budget" method="get">
                <input type="hidden" name="action" value="budget"/>
                <button type="submit">Бюджет</button>
            </form>
            <br/>
            <form action="financial-goal" method="get">
                <input type="hidden" name="action" value="financial"/>
                <button type="submit">Долгосрочные финансовые цели</button>
            </form>
            <br/>
            <form action="family" method="get">
                <input type="hidden" name="action" value="family"/>
                <button type="submit">Семейная группа</button>
            </form>
            <br/>
            <form action="person" method="get">
                <input type="hidden" name="action" value="update"/>
                <button type="submit">Обновить данные пользователя</button>
            </form>
            <br/>
            <form action="person" method="post">
                <input type="hidden" name="action" value="delete"/>
                <button type="submit">Удалить учетную запись пользователя</button>
            </form>
            <br/>
            <br/>
            <form action="logout" method="get">
                <input type="hidden" name="action" value="exit"/>
                <button type="submit">Выйти из системы</button>
            </form>
            <br/>
        </div>
    </div>
    </body>
</html>