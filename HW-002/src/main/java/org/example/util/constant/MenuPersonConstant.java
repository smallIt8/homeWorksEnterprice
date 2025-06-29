package org.example.util.constant;

import org.example.util.AppUtil;

public final class MenuPersonConstant {

    // ========== Сообщения о выполняемых действиях ==========
    public static final String ADDING_MESSAGE = AppUtil.colorizeGreenText("Создание сотрудника:");
    public static final String FINISHING_MESSAGE = "Завершение программы";

    // ========== Информационные сообщения ==========
    public static final String LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников:");
    public static final String EMPTY_LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников пуст");
    public static final String PERSON_NOT_FOUND_MESSAGE = AppUtil.colorizeGreenText("Сотрудник с таким ID не найден");
    public static final String UPDATE_PERSON_MESSAGE = AppUtil.colorizeGreenText("Сотрудник найден и готов к обновлению. ID сотрудника: ");

    // ========== Сообщения о выполненных действиях ==========
    public static final String ADDED_MESSAGE = AppUtil.colorizeGreenText("Вы добавили сотрудника.");
    public static final String UPDATED_MESSAGE = AppUtil.colorizeGreenText("Вы обновили данные сотрудника. ");
    public static final String DELETED_MESSAGE = AppUtil.colorizeGreenText("Вы удалили данные сотрудника с ID: ");

    // ========== Инструкции пользователю ==========
    public static final String ENTER_FIRST_NAME = AppUtil.colorizeOrangeText("введите имя: ");
    public static final String ENTER_LAST_NAME = AppUtil.colorizeOrangeText("введите фамилию: ");
    public static final String ENTER_EMAIL = AppUtil.colorizeOrangeText("введите email: ");
    public static final String ENTER_SALARY = AppUtil.colorizeOrangeText("введите зарплату: ");
    public static final String ENTER_DEPARTMENT = AppUtil.colorizeOrangeText("введите департамент: ");
    public static final String ENTER_ID = AppUtil.colorizeOrangeText("введите ID сотрудника: ");

    // ========== Меню MAIN ==========
    public static final String MAIN_MENU = """
            
                  Hello GLd-JD2-17-25!
            Выберите необходимый пункт меню:
            1. Добавить сотрудника
            2. Просмотреть данные всех сотрудников
            3. Найти сотрудника по ID
            4. Обновить данные сотрудника по ID
            5. Удалить сотрудника по ID
            0. Выход
            
            ввод:\s""";

    private MenuPersonConstant() {
    }
}