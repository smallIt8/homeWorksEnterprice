package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexConstant {

	public static final String USERNAME_REGEX = "^[a-zA-Z0-9]{4,100}$";
	public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9_]{8,25}$";
	public static final String FIRST_AND_LAST_NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ]{2,50}$";
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]{4,25}@[a-zA-Z0-9-]{2,25}\\.[a-zA-Z]{2,10}$";
	public static final String TRANSACTION_NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ\\s\\-]{2,50}$";
	public static final String BUDGET_NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ\\s\\-]{2,50}$";
	public static final String FINANCIAL_GOAL_NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ\\s\\-]{2,50}$";
	public static final String CATEGORY_NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ]{2,50}$";
	public static final String FAMILY_NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ]{2,50}$";

	//TODO: Будет использовано в следующем ДЗ
	public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
	public static final String YES_OR_NO_REGEX = "^[yYnN]$";
}
