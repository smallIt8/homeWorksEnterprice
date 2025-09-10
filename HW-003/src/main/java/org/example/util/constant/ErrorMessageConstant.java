package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessageConstant {
	// ========== Сообщения об ошибках создания ==========
	public static final String ERROR_CREATION_USER_NAME_MESSAGE = "Пользователь с таким логином уже зарегистрирован.";
	public static final String ERROR_CREATION_EMAIL_MESSAGE = "Пользователь с таким email уже существует.";
	public static final String ERROR_CREATION_BUDGET_MESSAGE = "Бюджет можно устанавливать только на категории расходов.";
	public static final String ERROR_CREATION_FINANCIAL_GOAL_TARGET_AMOUNT_MESSAGE = "Целевая сумма не может быть пустой, о или быть меньше 0";
	public static final String ERROR_CREATION_TRANSACTION_AMOUNT_MESSAGE = "Стоимость транзакции не может быть пустой, 0 или быть меньше 0";
	public static final String ERROR_CREATION_BUDGET_LIMIT_MESSAGE = "Устанавливаемый лимит бюджета не может быть пустым, 0 или быть меньше 0";
	public static final String ERROR_CREATION_TRANSACTION_DATE_MESSAGE = "Дата транзакции не может быть пустой и не может быть позже текущей даты.";
	public static final String ERROR_CREATION_FINANCIAL_GOAL_END_DATE_MESSAGE = "Конечная дата долгосрочной финансовой цели не может быть пустой и не может быть раньше текущей даты.";
	public static final String ERROR_CREATION_BUDGET_PERIOD_MESSAGE = "Устанавливаемый период бюджета не может быть пустым и не может быть раньше текущего месяца.";
	public static final String ERROR_SELECT_TRANSACTION_TYPE_MESSAGE = "Тип транзакции не может быть пустым";
	public static final String ERROR_SELECT_CATEGORY_TYPE_MESSAGE = "Тип категории не может быть пустым";
	public static final String ERROR_SELECT_TRANSACTION_CATEGORY_MESSAGE = "Категория в транзакции не может быть пустой";
	public static final String ERROR_SELECT_BUDGET_CATEGORY_MESSAGE = "Категория в бюджете не может быть пустой";

	// ========== Сообщения об ошибках доступа ==========
	public static final String ERROR_ACCESS_TRANSACTION_MESSAGE = "Ошибка доступа. Транзакция не принадлежит текущему пользователю";
	public static final String ERROR_ACCESS_FINANCIAL_GOAL_MESSAGE = "Ошибка доступа. Долгосрочная финансовая цель не принадлежит текущему пользователю";
	public static final String ERROR_ACCESS_BUDGET_MESSAGE = "Ошибка доступа. Бюджет не принадлежит текущему пользователю";
	public static final String ERROR_ACCESS_CATEGORY_MESSAGE = "Ошибка доступа. Категория не принадлежит текущему пользователю";
	public static final String ERROR_ACCESS_FAMILY_MESSAGE = "Ошибка доступа. Семейная группа не принадлежит текущему пользователю";

	// ========== Сообщения об ошибках обновления ==========
	public static final String ERROR_UPDATE_PERSON_PASSWORD_MESSAGE = "Не удалось обновить пароль текущего пользователя";

	// ========== Сообщения об ошибках ввода ==========
	public static final String ERROR_ENTER_MESSAGE = "Неверный ввод.\nповторите ввод: ";
	public static final String ERROR_ENTER_MAX_ATTEMPTS_MESSAGE = "Превышено допустимое количество попыток ввода.";
	public static final String ERROR_ENTER_USER_NAME_OR_PASSWORD_MESSAGE = "Неверный логин или пароль.\nПовторите вход.";
	public static final String ERROR_ENTER_NUMBER_MESSAGE = "Неверный ввод.\nЗапись с таким номером отсутствует.\nПовторите ввод.";
	public static final String ERROR_ENTER_USER_NAME_MESSAGE =
			"""
					Неверный ввод.
					Логин должен содержать от 4 до 100 символов, включая буквы и цифры.
					""";

	public static final String ERROR_ENTER_PASSWORD_MESSAGE =
			"""
					Неверный ввод.
					Пароль должен содержать от 8 до 25 символов, включая специальный символ '_',
					как минимум одну заглавную букву, одну строчную букву и одну цифру.
					""";

	public static final String ERROR_ENTER_FIRST_NAME_MESSAGE =
			"""
					Неверный ввод.
					Имя должно содержать от 2 до 50 символов
					включая буквы.
					""";

	public static final String ERROR_ENTER_LAST_NAME_MESSAGE =
			"""
					Неверный ввод.
					Фамилия должна содержать от 2 до 50 символов
					включая буквы.
					""";

	public static final String ERROR_ENTER_EMAIL_MESSAGE =
			"""
					Неверный ввод.
					Email должен быть в формате '*****@****.***',
					содержать от 4 до 25 символов перед '@', включая буквы, цифры и специальные символы '_-',
					содержать от 2 до 25 символов между '@' и '.', включая буквы, цифры и специальный символ '-',
					содержать от 2 до 10 символов после '.', включая только буквы.
					""";

	public static final String ERROR_ENTER_UUID_MESSAGE =
			"""
					Неверный ввод.
					ID сотрудника должен быть в формате UUID (********-****-****-****-************),
					содержать 8 символов перед первым дефисом,
					содержать 4 символа между первым и вторым дефисом,
					содержать 4 символа между вторым и третьим дефисом,
					содержать 4 символа между третьим и четвертым дефисом,
					содержать 12 символов после четвертого дефиса
					и состоять только из латинских букв от A до F в верхнем и нижнем регистре и цифр 0–9.
					""";

	public static final String ERROR_ENTER_YES_OR_NO_MESSAGE =
			"""
					Неверный ввод.
					Ответ должен быть только Y(Да) или N(Нет)
					""";

	public static final String ERROR_ENTER_TRANSACTION_NAME_MESSAGE =
			"""
					Неверный ввод.
					Имя должно содержать от 2 до 50 символов
					включая буквы.
					""";

	public static final String ERROR_ENTER_BUDGET_NAME_MESSAGE =
			"""
					Неверный ввод.
					Имя должно содержать от 2 до 50 символов
					включая буквы.
					""";

	public static final String ERROR_ENTER_FINANCIAL_GOAL_NAME_MESSAGE =
			"""
					Неверный ввод.
					Имя должно содержать от 2 до 50 символов
					включая буквы.
					""";

	public static final String ERROR_ENTER_CATEGORY_NAME_MESSAGE =
			"""
					Неверный ввод.
					Имя должно содержать от 2 до 50 символов
					включая буквы.
					""";

	public static final String ERROR_ENTER_FAMILY_NAME_MESSAGE =
			"""
					Неверный ввод.
					Имя должно содержать от 2 до 50 символов
					включая буквы.
					""";
}