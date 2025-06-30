package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Person;
import org.example.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.*;

@Slf4j

public class PersonRepositoryImpl implements PersonRepository {
    @Override
    public void create(Person person) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CREATE_PERSON)) {
            statement.setObject(1, person.getPersonId());
            statement.setString(2, person.getFirstName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getEmail());
            statement.setBigDecimal(5, person.getSalary());
            statement.setString(6, person.getDepartment());
            statement.execute();
        } catch (SQLException e) {
            log.error("Ошибка при создании сотрудника: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void createBatch(List<Person> persons) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CREATE_PERSON)) {
            for (Person person : persons) {
                statement.setObject(1, person.getPersonId());
                statement.setString(2, person.getFirstName());
                statement.setString(3, person.getLastName());
                statement.setString(4, person.getEmail());
                statement.setBigDecimal(5, person.getSalary());
                statement.setString(6, person.getDepartment());
                statement.addBatch();
            }
            int[] result = statement.executeBatch();
            log.info("Добавлено сотрудников: {}", result.length);
        } catch (SQLException e) {
            log.error("Ошибка при массовом добавлении сотрудников: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    @Override
    public boolean checkEmail(String email) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_PERSON)) {
            statement.setString(1, email.toLowerCase());
            ResultSet query = statement.executeQuery();
            if (query.next()) {
                return query.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            log.error("Ошибка при проверки введенного email: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PERSON)) {
            try (ResultSet query = statement.executeQuery()) {
                while (query.next()) {
                    UUID personId = UUID.fromString(query.getString("person_id"));
                    String firstName = query.getString("first_name");
                    String lastName = query.getString("last_name");
                    String email = query.getString("email");
                    BigDecimal salary = query.getBigDecimal("salary");
                    String department = query.getString("department");
                    Person person = new Person(personId, firstName, lastName, email, salary, department);
                    people.add(person);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении данных всех сотрудников: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return people;
    }

    @Override
    public List<Person> getAllBySalary() {
        List<Person> people = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PERSON_BY_SALARY)) {
            try (ResultSet query = statement.executeQuery()) {
                while (query.next()) {
                    Person person = new Person(
                            UUID.fromString(query.getString("person_id")),
                            query.getString("first_name"),
                            query.getString("last_name"),
                            query.getString("email"),
                            query.getBigDecimal("salary"),
                            query.getString("department")
                    );
                    people.add(person);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении сотрудников с сортировкой по зарплате: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return people;
    }

    @Override
    public List<Person> getAllByCreateDate() {
        List<Person> people = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PERSON_BY_DATE)) {
            try (ResultSet query = statement.executeQuery()) {
                while (query.next()) {
                    Person person = new Person(
                            UUID.fromString(query.getString("person_id")),
                            query.getString("first_name"),
                            query.getString("last_name"),
                            query.getString("email"),
                            query.getBigDecimal("salary"),
                            query.getString("department")
                    );
                    people.add(person);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении сотрудников с сортировкой по дате приема: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return people;
    }

    @Override
    public List<Person> getByLastName(String lastNameSearch) {
        List<Person> people = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(GET_BY_LAST_NAME_PERSON)) {
            statement.setString(1, "%" + lastNameSearch + "%");
            try (ResultSet query = statement.executeQuery()) {
                while (query.next()) {
                    Person person = new Person(
                            UUID.fromString(query.getString("person_id")),
                            query.getString("first_name"),
                            query.getString("last_name"),
                            query.getString("email"),
                            query.getBigDecimal("salary"),
                            query.getString("department")
                    );
                    people.add(person);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при поиске сотрудников по фамилии: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return people;
    }


    @Override
    public Optional<Person> getById(UUID personId) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_PERSON)) {
            statement.setObject(1, personId);
            try (ResultSet query = statement.executeQuery()) {
                if (query.next()) {
                    Person person = new Person();
                    person.setPersonId(personId);
                    person.setFirstName(query.getString("first_name"));
                    person.setLastName(query.getString("last_name"));
                    person.setEmail(query.getString("email"));
                    person.setSalary(query.getBigDecimal("salary"));
                    person.setDepartment(query.getString("department"));
                    return Optional.of(person);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при получении данных сотрудника: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void updateById(Person person) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_PERSON)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setBigDecimal(4, person.getSalary());
            statement.setString(5, person.getDepartment());
            statement.setObject(6, person.getPersonId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка при обновлении данных сотрудника: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean checkEmail(String email, UUID excludeId) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CHECK_UPDATE_EMAIL_PERSON)) {
            statement.setString(1, email.toLowerCase());
            statement.setObject(2, excludeId);
            ResultSet query = statement.executeQuery();
            if (query.next()) {
                return query.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(UUID personId) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_PERSON)) {
            statement.setObject(1, personId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка при удалении данных сотрудника: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}