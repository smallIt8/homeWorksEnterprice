package org.example.util.constant;

import lombok.experimental.UtilityClass;
import org.example.util.AppUtil;

@UtilityClass

public class MenuPersonConstant {

    // ========== Сообщения о выполняемых действиях ==========
    public final String ADDING_MESSAGE = AppUtil.colorizeGreenText("Создание сотрудника:");
    public final String FINISHING_MESSAGE = "Завершение программы";

    // ========== Информационные сообщения ==========
    public final String LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников:");
    public final String EMPTY_LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников пуст");
    public final String PERSON_NOT_FOUND_MESSAGE = AppUtil.colorizeGreenText("Сотрудник с таким ID не найден");
    public final String UPDATE_PERSON_MESSAGE = AppUtil.colorizeGreenText("Сотрудник найден и готов к обновлению. ID сотрудника: ");

    // ========== Сообщения о выполненных действиях ==========
    public final String ADDED_MESSAGE = AppUtil.colorizeGreenText("Вы добавили сотрудника.");
    public final String UPDATED_MESSAGE = AppUtil.colorizeGreenText("Вы обновили данные сотрудника. ");
    public final String DELETED_MESSAGE = AppUtil.colorizeGreenText("Вы удалили данные сотрудника с ID: ");

    // ========== Инструкции пользователю ==========
    public final String ENTER_FIRST_NAME = AppUtil.colorizeOrangeText("введите имя: ");
    public final String ENTER_LAST_NAME = AppUtil.colorizeOrangeText("введите фамилию: ");
    public final String ENTER_EMAIL = AppUtil.colorizeOrangeText("введите email: ");
    public final String ENTER_SALARY = AppUtil.colorizeOrangeText("введите зарплату: ");
    public final String ENTER_DEPARTMENT = AppUtil.colorizeOrangeText("введите департамент: ");
    public final String ENTER_ID = AppUtil.colorizeOrangeText("введите ID сотрудника: ");

    // ========== Меню MAIN ==========
    public final String MAIN_MENU = """
            
                  Hello GLd-JD2-17-25!
            Выберите необходимый пункт меню:
            1. Добавить сотрудника
            2. Просмотреть данные всех сотрудников
            3. Найти сотрудника по ID
            4. Обновить данные сотрудника по ID
            5. Удалить сотрудника по ID
            0. Выход
            
            ввод:\s""";
}