<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru,en">
    <head>
        <title>Обновление данных пользователя</title>
    </head>
    <body>
        <div style="text-align: left;">
            <h1 style="text-align: center;">Обновление данных пользователя: <span style="color: blue;">${personName}</span></h1>
            <div>
                <c:if test="${action == 'update'}">
                    <h3>Выберите необходимое действие:</h3>
                    <form action="${pageContext.request.contextPath}/main-person" method="get">
                        <input type="hidden" name="action" value="update-person"/>
                        <button type="submit">Обновить данные пользователя</button>
                    </form>
                    <br/>
                    <form action="${pageContext.request.contextPath}/main-person" method="get">
                        <input type="hidden" name="action" value="update-password"/>
                        <button type="submit">Обновить пароль пользователя</button>
                    </form>
                    <br/>
                    <br/>
                    <form action="${pageContext.request.contextPath}/main-person" method="get">
                        <input type="hidden" name="action" value="back"/>
                        <button type="submit">Вернуться назад</button>
                    </form>
                    <br/>
                    <form action="${pageContext.request.contextPath}/logout" method="get">
                        <button type="submit">Выйти из системы</button>
                    </form>
                    <br/>
                </c:if>
            </div>

            <div>
                <c:if test="${action == 'update-person'}">
                    <h3>Обновление данных пользователя:</h3>
                    <form action="${pageContext.request.contextPath}/main-person" method="post">
                        <input type="hidden" name="action" value="updated-person"/>
                        <div>
                            <label>Имя:
                                <input type="text" name="firstName" value="${person.firstName}" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label>Фамилия:
                                <input type="text" name="lastName" value="${person.lastName}" required/>
                            </label>
                        </div>
                        <br/>
                        <div>
                            <label>Email:
                                <input type="email" name="email" value="${person.email}" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Сохранить изменения</button>
                    </form>
                </c:if>
            </div>

            <div>
                <c:if test="${action == 'update-password'}">
                    <h3>Обновление пароля пользователя:</h3>
                    <form action="${pageContext.request.contextPath}/main-person" method="post">
                        <input type="hidden" name="action" value="updated-password"/>
                        <div>
                            <label for="password">Новый пароль:
                            <input type="password" name="password" id="password" required/>
                            </label>
                        </div>
                        <br/>
                        <button type="submit">Сохранить пароль</button>
                    </form>
                </c:if>
            </div>
        </div>
    </body>
</html>