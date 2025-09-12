package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass

public class SqlConstant {
	public static final String ENTRY_PERSON = "SELECT person_id, password, first_name, last_name, email FROM person WHERE user_name = ?";
	public static final String CREATE_PERSON = "INSERT INTO person (person_id, user_name, password, first_name, last_name, email, family_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_ALL_PERSON = "SELECT * FROM person";
	public static final String GET_BY_LAST_NAME_PERSON = "SELECT * FROM person WHERE last_name ILIKE ?";
	public static final String GET_BY_ID_PERSON_JOIN = """
					SELECT p.person_id, p.user_name, p.first_name, p.last_name, p.email, p.family_id, p.create_date, f.family_name
					FROM person p
					LEFT JOIN family f
					ON p.family_id = f.family_id
					WHERE p.person_id = ?;
			""";

	public static final String GET_BY_ID_FAMILY = "SELECT * FROM family WHERE family_id = ?";
	public static final String UPDATE_BY_ID_PERSON = "UPDATE person SET first_name = ?, last_name = ?, email = ? WHERE person_id = ?";
	public static final String UPDATE_PASSWORD_BY_ID_PERSON = "UPDATE person SET password = ? WHERE person_id = ?";
	public static final String UPDATE_FAMILY_BY_ID_PERSON = "UPDATE person SET family_id = ? WHERE person_id = ?";
	public static final String DELETE_FAMILY_BY_ID_PERSON = "DELETE person SET family_id = ? WHERE person_id = ?";
	public static final String DELETE_BY_ID_PERSON = "DELETE FROM person WHERE person_id = ?";
	public static final String CHECK_USER_NAME_PERSON = "SELECT COUNT(*) FROM person WHERE user_name = ?";
	public static final String CHECK_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ?";
	public static final String CHECK_UPDATE_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ? AND person_id <> ?";
	public static final String GET_ALL_PERSON_BY_SALARY = "SELECT * FROM person ORDER BY salary";
	public static final String GET_ALL_PERSON_BY_DATE = "SELECT * FROM person ORDER BY create_date";
}