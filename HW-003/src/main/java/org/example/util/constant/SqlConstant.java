package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlConstant {
	public static final String ENTRY_PERSON = "SELECT person_id, password, first_name, last_name, email FROM person WHERE user_name = ?";
	public static final String CREATE_PERSON = "INSERT INTO person (person_id, user_name, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String CREATE_TRANSACTION = "INSERT INTO transaction (transaction_id, transaction_name, type, category_id, amount, person_id, transaction_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String CREATE_BUDGET = "INSERT INTO budget (budget_id, budget_name, category_id, budget_limit, period, person_id) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String CREATE_FINANCIAL_GOAL = "INSERT INTO financial_goal (financial_goal_id, financial_goal_name, target_amount, end_date, person_id) VALUES (?, ?, ?, ?, ?)";
	public static final String CREATE_CATEGORY = "INSERT INTO category (category_id, category_name, type, person_id) VALUES (?, ?, ?, ?)";
	public static final String CREATE_FAMILY = "INSERT INTO family (family_id, family_name, person_id) VALUES (?, ?, ?)";
	public static final String FIND_BY_CREATOR_ALL_TRANSACTION_JOIN = """
					SELECT t.transaction_id, t.transaction_name, t.type, t.category_id, t.amount, t.person_id, t.transaction_date, t.create_date, c.category_name
					FROM transaction t
					LEFT JOIN category c
					ON t.category_id = c.category_id
					WHERE t.person_id = ?;
			""";

	public static final String FIND_BY_CREATOR_ALL_BUDGET_JOIN = """
					SELECT  b.budget_id, b.budget_name, b.category_id, b.budget_limit, b.period, b.person_id, c.category_name
					FROM budget b
					LEFT JOIN category c
					ON b.category_id = c.category_id
					WHERE b.person_id = ?;
			""";

	public static final String FIND_BY_CREATOR_ALL_FINANCIAL_GOAL = "SELECT * FROM financial_goal WHERE person_id = ?";
	public static final String FIND_BY_CREATOR_ALL_CATEGORY = "SELECT * FROM category WHERE person_id = ?";
	public static final String FIND_BY_CREATOR_ALL_FAMILY = "SELECT * FROM family WHERE person_id = ?";
	public static final String FIND_BY_ID_PERSON = "SELECT person_id, user_name, first_name, last_name, email, create_date FROM person WHERE person_id = ?";
	public static final String FIND_BY_PERSON_FAMILIES = """
        SELECT f.family_id, f.family_name, f.person_id AS person_id
        FROM family_person fp
        JOIN family f
        ON fp.family_id = f.family_id
        WHERE fp.person_id = ?
    """;


	public static final String FIND_BY_ID_TRANSACTION_JOIN = """
					SELECT t.transaction_id, t.transaction_name, t.type, t.category_id, t.amount, t.person_id, t.transaction_date, c.category_name
					FROM transaction t
					LEFT JOIN category c
					ON t.category_id = c.category_id
					WHERE t.transaction_id = ?;
			""";

	public static final String FIND_BY_ID_BUDGET_JOIN = """
					SELECT b.budget_id, b.budget_name, b.category_id, b.budget_limit, b.period, b.person_id, c.category_name
					FROM budget b
					LEFT JOIN category c
					ON b.category_id = c.category_id
					WHERE b.budget_id = ?;
			""";

	public static final String FIND_BY_ID_FINANCIAL_GOAL = """
					SELECT financial_goal_id, financial_goal_name, target_amount, end_date, person_id
					FROM financial_goal
					WHERE financial_goal_id = ?;
			""";

	public static final String FIND_BY_ID_CATEGORY = "SELECT * FROM category WHERE category_id = ?";
	public static final String FIND_BY_ID_FAMILY = "SELECT * FROM family WHERE family_id = ?";
	public static final String UPDATE_BY_ID_PERSON = "UPDATE person SET first_name = ?, last_name = ?, email = ? WHERE person_id = ?";
	public static final String UPDATE_PASSWORD_BY_ID_PERSON = "UPDATE person SET password = ? WHERE person_id = ?";
	public static final String UPDATE_BY_ID_TRANSACTION = "UPDATE transaction SET transaction_name = ?, type = ?, category_id = ?, amount = ?, transaction_date = ? WHERE transaction_id = ?";
	public static final String UPDATE_BY_ID_BUDGET = "UPDATE budget SET budget_name = ?, category_id = ?, budget_limit = ?, period = ? WHERE budget_id = ?";
	public static final String UPDATE_BY_ID_FINANCIAL_GOAL = "UPDATE financial_goal SET financial_goal_name = ?, target_amount = ?, end_date = ? WHERE financial_goal_id = ?";
	public static final String UPDATE_BY_ID_CATEGORY = "UPDATE category SET category_name = ? WHERE category_id = ?";
	public static final String UPDATE_BY_ID_FAMILY = "UPDATE family SET family_name = ? WHERE family_id = ?";
	public static final String DELETE_BY_ID_PERSON = "DELETE FROM person WHERE person_id = ?";
	public static final String DELETE_BY_ID_TRANSACTION = "DELETE FROM transaction WHERE transaction_id = ? AND person_id = ?";
	public static final String DELETE_BY_ID_BUDGET = "DELETE FROM budget WHERE budget_id = ? AND person_id = ?";
	public static final String DELETE_BY_ID_FINANCIAL_GOAL = "DELETE FROM financial_goal WHERE financial_goal_id = ? AND person_id = ?";
	public static final String DELETE_BY_ID_CATEGORY = "DELETE FROM category WHERE category_id = ? AND person_id = ?";
	public static final String DELETE_BY_ID_FAMILY = "DELETE FROM family WHERE family_id = ? AND person_id = ?";
	public static final String CHECK_USER_NAME_PERSON = "SELECT COUNT(*) FROM person WHERE user_name = ?";
	public static final String CHECK_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ?";
	public static final String CHECK_UPDATE_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ? AND person_id <> ?";
}