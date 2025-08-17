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
	public static final String NOT_FOUND_PERSON_MESSAGE = "ID текущего пользователя не найден в БД";
	public static final String UPDATE_PERSON_MESSAGE = AppUtil.colorizeGreenText("Пользователь найден и готов к обновлению. Имя пользователя: ");
	public static final String UPDATE_PERSON_PASSWORD_MESSAGE = AppUtil.colorizeGreenText("Пользователь найден, пароль пользователя готов к обновлению. Имя пользователя: ");
	public static final String REDIRECT_CREATE_FAMILY_MESSAGE = AppUtil.colorizeGreenText("Перенаправление в меню создания семейной группы");

	// ========== Предупреждающие сообщения ==========
	public static final String WARNING_DELETE_PERSON_MESSAGE =
			"""
					Внимание! Это действие необратимо.
					Вы действительно хотите удалить свою учетную запись?
					""";

	public static final String WARNING_DELETE_TRANSACTION_MESSAGE =
			"""
					Внимание! Это действие необратимо.
					Вы действительно хотите удалить выбранную транзакцию?
					""";

	public static final String WARNING_DELETE_BUDGET_MESSAGE =
			"""
					Внимание! Это действие необратимо.
					Вы действительно хотите удалить выбранный бюджет?
					""";

	public static final String WARNING_DELETE_CATEGORY_MESSAGE =
			"""
					Внимание! Это действие необратимо.
					Вы действительно хотите удалить выбранную категорию?
					""";

	public static final String WARNING_DELETE_FINANCIAL_GOAL_MESSAGE =
			"""
					Внимание! Это действие необратимо.
					Вы действительно хотите удалить выбранную долгосрочную финансовую цель?
					""";

	public static final String WARNING_DELETE_FAMILY_MESSAGE =
			"""
					Внимание! Это действие необратимо.
					Вы действительно хотите удалить выбранную Семейную группу?
					""";

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

	//  ========== Меню PERSON/TRANSACTION
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


	//  ========== Меню PERSON/BUDGET ==========
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
}