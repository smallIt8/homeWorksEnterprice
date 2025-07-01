package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass

public class SqlConstant {
    public static final String CREATE_PERSON = "INSERT INTO person (person_id, first_name, last_name, email, salary, department) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_PERSON = "SELECT * FROM person";
    public static final String GET_BY_LAST_NAME_PERSON = "SELECT * FROM person WHERE last_name ILIKE ?";
    public static final String GET_BY_ID_PERSON = "SELECT * FROM person WHERE person_id = ?";
    public static final String UPDATE_BY_ID_PERSON = "UPDATE person SET first_name = ?, last_name = ?, email = ?, salary = ?, department = ? WHERE person_id = ?";
    public static final String DELETE_BY_ID_PERSON = "DELETE FROM person WHERE person_id = ?";
    public static final String CHECK_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ?";
    public static final String CHECK_UPDATE_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ? AND person_id <> ?";
    public static final String GET_ALL_PERSON_BY_SALARY = "SELECT * FROM person ORDER BY salary";
    public static final String GET_ALL_PERSON_BY_DATE = "SELECT * FROM person ORDER BY create_date"; // если есть колонка hire_date
}