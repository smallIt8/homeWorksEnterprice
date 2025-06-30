package org.example.util.constant;

import lombok.experimental.UtilityClass;
import org.example.util.AppUtil;

@UtilityClass

public class MenuPersonConstant {

    // ========== Сообщения о выполняемых действиях ==========
    public static final String ADDING_MESSAGE = AppUtil.colorizeGreenText("Добавление сотрудника: ");
    public static final String ADDING_BATCH_MESSAGE = AppUtil.colorizeGreenText("Запуск пакетного добавления сотрудников:");
    public static final String FINISHING_MESSAGE = "Завершение программы";

    // ========== Информационные сообщения ==========
    public static final String LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников:");
    public static final String LIST_PERSON_BY_SALARY_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников отсортированный по зарплате:");
    public static final String LIST_PERSON_BY_CREATE_DATE_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников отсортированный по дате приема:");
    public static final String LIST_PERSON_LAST_NAME_SEARCH_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников с введенной фамилией:");
    public static final String EMPTY_LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников пуст");
    public static final String EMPTY_LIST_LAST_NAME_SEARCH_PERSON_MESSAGE = AppUtil.colorizeGreenText("Сотрудники с такой фамилией не найдены");
    public static final String PERSON_NOT_FOUND_MESSAGE = AppUtil.colorizeGreenText("Сотрудник с таким ID не найден");
    public static final String UPDATE_PERSON_MESSAGE = AppUtil.colorizeGreenText("Сотрудник найден и готов к обновлению. ID сотрудника: ");

    // ========== Сообщения о выполненных действиях ==========
    public static final String ADDED_MESSAGE = AppUtil.colorizeGreenText("Вы добавили сотрудника.");
    public static final String ADDED_PERSONS_MESSAGE = AppUtil.colorizeGreenText("Все сотрудники добавлены.");
    public static final String UPDATED_MESSAGE = AppUtil.colorizeGreenText("Вы обновили данные сотрудника. ");
    public static final String DELETED_MESSAGE = AppUtil.colorizeGreenText("Вы удалили данные сотрудника с ID: ");

    // ========== Инструкции пользователю ==========
    public static final String ENTER_FIRST_NAME = AppUtil.colorizeOrangeText("введите имя: ");
    public static final String ENTER_LAST_NAME = AppUtil.colorizeOrangeText("введите фамилию: ");
    public static final String ENTER_LAST_NAME_SEARCH = AppUtil.colorizeOrangeText("введите фамилию для поиска сотрудника: ");
    public static final String ENTER_EMAIL = AppUtil.colorizeOrangeText("введите email: ");
    public static final String ENTER_SALARY = AppUtil.colorizeOrangeText("введите зарплату: ");
    public static final String ENTER_DEPARTMENT = AppUtil.colorizeOrangeText("введите департамент: ");
    public static final String ENTER_ID = AppUtil.colorizeOrangeText("введите ID сотрудника: ");
    public static final String ADDING_PERSON = AppUtil.colorizeOrangeText("добавить ещё одного сотрудника? (y/n): ");

    // ========== Меню MAIN ==========
    public static final String MAIN_MENU = """
            
                  Hello GLd-JD2-17-25!
            Выберите необходимый пункт меню:
            1. Добавить сотрудника
            2. Пакетное добавление сотрудников
            3. Просмотреть данные всех сотрудников
            4. Просмотреть данные сотрудников с сортировкой по зарплатой
            5. Просмотреть данные сотрудников с сортировкой по дате добавления
            6. Найти сотрудника по фамилии
            7. Найти сотрудника по ID
            8. Обновить данные сотрудника по ID
            9. Удалить сотрудника по ID
            
            0. Выход
            
            ввод:\s""";
}