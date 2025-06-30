package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass

public class SqlConstant {
    public final String CREATE_PERSON = "INSERT INTO person (person_id, first_name, last_name, email, salary, department) VALUES (?, ?, ?, ?, ?, ?)";
    public final String GET_ALL_PERSON = "SELECT * FROM person";
    public final String GET_BY_ID_PERSON = "SELECT * FROM person WHERE person_id = ?";
    public final String UPDATE_BY_ID_PERSON = "UPDATE person SET first_name = ?, last_name = ?, email = ?, salary = ?, department = ? WHERE person_id = ?";
    public final String DELETE_BY_ID_PERSON = "DELETE FROM person WHERE person_id = ?";
    public final String CHECK_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ?";
    public final String CHECK_UPDATE_EMAIL_PERSON = "SELECT COUNT(*) FROM person WHERE email = ? AND person_id <> ?";
}