package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InfoMessageConstant {

	// ========== Сообщения о выполняемых действиях ==========

	//TODO: Будет использовано на Spring
	public static final String WELCOME_MESSAGE = "Добро пожаловать ";
	public static final String REGISTRATION_PERSON_MESSAGE = "Регистрация:";
	public static final String CREATION_FAMILY_MESSAGE = "Создание семейной группы:";
	public static final String ADDING_BATCH_MESSAGE = "Запуск пакетного добавления сотрудников:";
	public static final String FINISHING_MESSAGE = "Завершение программы.";

	// ========== Информационные сообщения ==========
	public static final String EMPTY_LIST_FAMILY_BY_PERSON_MESSAGE = "Пользователь не состоит в семейных группах";
	public static final String EMPTY_LIST_FAMILY_BY_OWNER_PERSON_MESSAGE = "Список семейных групп, созданных пользователем пуст";
	public static final String EMPTY_LIST_TRANSACTION_BY_PERSON_MESSAGE = "Список транзакций, созданных пользователем пуст";
	public static final String EMPTY_LIST_FINANCIAL_GOAL_BY_PERSON_MESSAGE = "Список долгосрочных финансовых целей, созданных пользователем пуст";
	public static final String EMPTY_LIST_BUDGET_BY_PERSON_MESSAGE = "Список бюджетов, созданных пользователем пуст";
	public static final String EMPTY_LIST_CATEGORY_BY_PERSON_MESSAGE = "Список категорий, созданных пользователем пуст";
	public static final String NOT_FOUND_PERSON_MESSAGE = "ID текущего пользователя не найден в БД";
	public static final String NOT_FOUND_PERSON_SESSION_MESSAGE = "Пользователь не найден в сессии";
	public static final String NOT_FOUND_TRANSACTION_MESSAGE = "ID транзакции не найден в БД";
	public static final String NOT_FOUND_FAMILY_MESSAGE = "ID семейной группы не найден в БД";
	public static final String NOT_FOUND_FINANCIAL_GOAL_MESSAGE = "ID долгосрочной финансовой цели не найден в БД";
	public static final String NOT_FOUND_BUDGET_MESSAGE = "ID бюджета не найден в БД";
	public static final String NOT_FOUND_CATEGORY_MESSAGE = "ID категории не найден в БД";

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
					Вы действительно хотите удалить выбранную семейную группу?
					""";

	// ========== Сообщения о выполненных действиях ==========

	//TODO: Будет использовано на Spring
	public static final String REGISTERED_MESSAGE = "Вы зарегистрировались.";
	public static final String CREATED_FAMILY_MESSAGE = "Вы создали семейную группу.";
	public static final String ADDED_PERSONS_MESSAGE = "Пакетный ввод сотрудников завершен.";
	public static final String UPDATED_PERSON_MESSAGE = "Вы обновили данные пользователя.";
	public static final String UPDATED_PERSON_PASSWORD_MESSAGE = "Вы обновили пароль пользователя.";
	public static final String UPDATED_FAMILY_MESSAGE = "Вы обновили имя семейной группы.";
	public static final String DELETED_MESSAGE = "Вы удалили данные пользователя с ID: ";

	// ========== Инструкции пользователю ==========

	//TODO: Будет использовано на Spring
	public static final String ADDING_PERSON = "добавить ещё одного сотрудника? Введите Y(Да) или N(Нет): ";

	//  ========== Меню PERSON/TRANSACTION

	//TODO: Будет использовано на Spring
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
			""";

	//  ========== Меню PERSON/BUDGET ==========

	//TODO: Будет использовано на Spring
	public static final String PERSON_BUDGET_LIST_MENU = """
			
			Кабинет пользователя/Бюджет/Список бюджетов
			
			Выберите необходимый пункт меню:
			1. Просмотреть список бюджетов текущего пользователя
			2. Отфильтровать список бюджетов за выбранный период
			3. Отфильтровать список бюджетов по выбранной категории
			4. Отфильтровать список бюджетов по выбранной категории за выбранный период
			""";
}
