package org.example.util.constant;

import lombok.experimental.UtilityClass;
import org.example.util.AppUtil;

@UtilityClass

public class MenuConstant {

	// ========== Сообщения о выполняемых действиях ==========
	public static final String WELCOME_MESSAGE = "Добро пожаловать ";
	public static final String REGISTRATION_PERSON_MESSAGE = "Регистрация:";
	public static final String CREATION_FAMILY_MESSAGE = "Создание семейной группы:";
	public static final String ADDING_BATCH_MESSAGE = AppUtil.colorizeGreenText("Запуск пакетного добавления сотрудников:");
	public static final String FINISHING_MESSAGE = "Завершение программы.";

	// ========== Информационные сообщения ==========
	public static final String LIST_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников:");
	public static final String LIST_FAMILY_BY_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список семейных групп, созданных пользователем:");
	public static final String LIST_PERSON_BY_CREATE_DATE_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников отсортированный по дате приема:");
	public static final String LIST_PERSON_LAST_NAME_SEARCH_MESSAGE = AppUtil.colorizeGreenText("Список сотрудников с фамилией:");
	public static final String EMPTY_LIST_FAMILY_BY_PERSON_MESSAGE = AppUtil.colorizeGreenText("Список семейных групп, созданных пользователем пуст");
	public static final String EMPTY_LIST_LAST_NAME_SEARCH_PERSON_MESSAGE = AppUtil.colorizeGreenText("Сотрудники с такой фамилией не найдены");
	public static final String PERSON_NOT_FOUND_MESSAGE = AppUtil.colorizeGreenText("ID текущего пользователя не найден в БД");
	public static final String UPDATE_PERSON_MESSAGE = AppUtil.colorizeGreenText("Пользователь найден и готов к обновлению. Имя пользователя: ");
	public static final String UPDATE_PERSON_PASSWORD_MESSAGE = AppUtil.colorizeGreenText("Пользователь найден, пароль пользователя готов к обновлению. Имя пользователя: ");
	public static final String REDIRECT_CREATE_FAMILY_MESSAGE = AppUtil.colorizeGreenText("Перенаправление в меню создания семейной группы");

	// ========== Сообщения о выполненных действиях ==========
	public static final String REGISTERED_MESSAGE = AppUtil.colorizeGreenText("Вы зарегистрировались.");
	public static final String CREATED_FAMILY_MESSAGE = AppUtil.colorizeGreenText("Вы создали семейную группу.");
	public static final String SELECTED_DATA_MESSAGE = AppUtil.colorizeGreenText("Вы выбрали данные: ");
	public static final String ADDED_PERSONS_MESSAGE = AppUtil.colorizeGreenText("Пакетный ввод сотрудников завершен.");
	public static final String UPDATED_PERSON_MESSAGE = AppUtil.colorizeGreenText("Вы обновили данные пользователя.");
	public static final String UPDATED_PERSON_PASSWORD_MESSAGE = AppUtil.colorizeGreenText("Вы обновили пароль пользователя.");
	public static final String UPDATED_FAMILY_MESSAGE = AppUtil.colorizeGreenText("Вы обновили имя семейной группы.");
	public static final String DELETED_MESSAGE = AppUtil.colorizeGreenText("Вы удалили данные пользователя с ID: ");

	// ========== Инструкции пользователю ==========
	public static final String ENTER_USERNAME = AppUtil.colorizeOrangeText("введите логин: ");
	public static final String ENTER_PASSWORD = AppUtil.colorizeOrangeText("введите пароль: ");
	public static final String ENTER_FIRST_NAME = AppUtil.colorizeOrangeText("введите имя: ");
	public static final String ENTER_LAST_NAME = AppUtil.colorizeOrangeText("введите фамилию: ");
	public static final String ENTER_LAST_NAME_SEARCH = AppUtil.colorizeOrangeText("введите фамилию для поиска сотрудника: ");
	public static final String ENTER_EMAIL = AppUtil.colorizeOrangeText("введите email: ");
	public static final String ENTER_ID = AppUtil.colorizeOrangeText("введите ID сотрудника: ");
	public static final String ENTER_FAMILY_NAME = AppUtil.colorizeOrangeText("введите имя семейной группы: ");
	public static final String ADDING_PERSON = AppUtil.colorizeOrangeText("добавить ещё одного сотрудника? Введите Y(Да) или N(Нет): ");
	public static final String ENTER_NUMBER_IN_LIST = AppUtil.colorizeOrangeText("введите номер в списке: ");

	// ========== Меню MAIN ==========
	public static final String MAIN_MENU = """
			
			       Hello GLd-JD2-17-25!
			Выберите необходимый пункт меню:
			1. Вход
			2. Регистрация
			
			0. Выход
			
			ввод:\s""";

	//  ========== Меню PERSON ==========
	public static final String PERSON_MENU = """
			
			Кабинет пользователя
			
			Выберите необходимый пункт меню:
			1. Транзакции
			2. Категории
			3. Бюджет
			4. Долгосрочные финансовые цели
			5. Семейная группа
			6. Обновить данные пользователя
			9. Удалить учетную запись пользователя
			
			0. Выход
			
			ввод:\s""";

	public static final String PERSON_UPDATE_MENU = """
			
			Кабинет пользователя/Обновить данные пользователя
			
			Выберите необходимый пункт меню:
			1. Обновить данные пользователя
			2. Обновить пароль пользователя
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	//  ========== Меню PERSON/TRANSACTION
	public static final String PERSON_TRANSACTION_MENU = """
			
			Кабинет пользователя/Транзакции
			
			Выберите необходимый пункт меню:
			1. Добавить транзакцию
			2. Обновить данные транзакции
			3. Просмотреть список транзакций
			9. Удалить транзакцию
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	public static final String PERSON_TRANSACTION_LIST_MENU = """
			
			Кабинет пользователя/Транзакции/Список транзакций
			
			Выберите необходимый пункт меню:
			1. Просмотреть список транзакций текущего пользователя
			2. Отфильтровать список по приходным транзакциям
			3. Отфильтровать список по приходным транзакциям за выбранный период
			4. Отфильтровать список по приходным транзакциям и категории
			5. Отфильтровать список по расходным транзакциям
			6. Отфильтровать список по расходным транзакциям за выбранный период
			7. Отфильтровать список по расходным транзакциям и категории
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	//  ========== Меню PERSON/CATEGORY ==========
	public static final String PERSON_CATEGORY_MENU = """
			
			Кабинет пользователя/Категории
			
			Выберите необходимый пункт меню:
			1. Создать категорию
			2. Обновить имя категории
			3. Просмотреть список категорий
			9. Удалить категорию
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	//  ========== Меню PERSON/BUDGET ==========
	public static final String PERSON_BUDGET_MENU = """
			
			Кабинет пользователя/Бюджет
			
			Выберите необходимый пункт меню:
			1. Установить бюджет на категорию
			2. Обновить данные бюджета
			3. Просмотреть список бюджетов
			9. Удалить бюджет
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	public static final String PERSON_BUDGET_LIST_MENU = """
			
			Кабинет пользователя/Бюджет/Список бюджетов
			
			Выберите необходимый пункт меню:
			1. Просмотреть список бюджетов текущего пользователя
			2. Отфильтровать список бюджетов за выбранный период
			3. Отфильтровать список бюджетов по выбранной категории
			4. Отфильтровать список бюджетов по выбранной категории за выбранный период
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	//  ========== Меню PERSON/FINANCIAL_GOAL ==========
	public static final String PERSON_GOAL_MENU = """
			
			Кабинет пользователя/Долгосрочные финансовые цели
			
			Выберите необходимый пункт меню:
			1. Создать долгосрочную финансовую цель
			2. Обновить данные долгосрочной финансовой цели
			3. Просмотреть список долгосрочных финансовых целей
			9. Удалить долгосрочную финансовую цель
			
			<. Назад
			0. Выход
			
			ввод:\s""";

	//  ========== Меню PERSON/FAMILY ==========
	public static final String PERSON_FAMILY_MENU = """
			
			Кабинет пользователя/Семейная группа
			
			Выберите необходимый пункт меню:
			1. Создать семейную группу
			2. Вступить в семейную группу
			3. Добавить пользователя в семейную группу
			4. Обновить имя семейной группы
			5. Выйти из семейной группы
			9. Удалить семейную группу
			
			<. Назад
			0. Выход
			
			ввод:\s""";
}