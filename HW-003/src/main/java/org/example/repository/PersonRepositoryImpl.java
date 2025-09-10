package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.model.Person;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.*;

@Slf4j
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

	@Override
	public Optional<Person> entry(String userName) {
		try (Connection connection = ConnectionManager.open()) {
			PreparedStatement statement = connection
					.prepareStatement(ENTRY_PERSON);
			statement.setString(1, userName);
			ResultSet query = statement.executeQuery();
			if (query.next()) {
				UUID personId = query.getObject("person_id", UUID.class);
				String hashedPassword = query.getString("password");
				String firstName = query.getString("first_name");
				String lastName = query.getString("last_name");
				String email = query.getString("email");
				Person person = Person.builder().personId(personId).password(hashedPassword).firstName(firstName).lastName(lastName).email(email).build();
				return Optional.of(person);
			}
		} catch (SQLException e) {
			log.error("Ошибка при авторизации пользователя с именем {}: {}", userName, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void create(Person person) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_PERSON)) {
			statement.setObject(1, person.getPersonId());
			statement.setString(2, person.getUserName());
			statement.setString(3, person.getPassword());
			statement.setString(4, person.getFirstName());
			statement.setString(5, person.getLastName());
			statement.setString(6, person.getEmail());
			statement.setNull(7, Types.OTHER);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при создании пользователя '{}': {}", person.getUserName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public boolean checkUserName(String userName) {
		try (Connection connection = ConnectionManager.open()) {
			PreparedStatement statement = connection
					.prepareStatement(CHECK_USER_NAME_PERSON);
			statement.setString(1, userName.toLowerCase());
			ResultSet query = statement.executeQuery();
			query.next();
			return query.getInt(1) > 0;
		} catch (Exception e) {
			log.error("Ошибка при проверке введенного имени пользователя '{}': {}", userName, e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
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
			log.error("Ошибка при проверки введенного email '{}': {}", email, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Person> findById(UUID personId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_PERSON_JOIN)) {
			statement.setObject(1, personId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Person person = new Person();
					person.setPersonId(personId);
					person.setFirstName(query.getString("first_name"));
					person.setLastName(query.getString("last_name"));
					person.setEmail(query.getString("email"));
					UUID familyId = query.getObject("family_id", UUID.class);
					Family family = null;
					if (familyId != null) {
						String familyName = query.getString("family_name");
						family = Family.builder().familyId(familyId).build();
						family.setName(familyName);
					}
					person.setFamily(family);
					person.setCreateDate(query.getTimestamp("create_date").toLocalDateTime());
					return Optional.of(person);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных пользователя с ID {}: {}", personId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void update(Person currentPerson) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_PERSON)) {
			statement.setString(1, currentPerson.getFirstName());
			statement.setString(2, currentPerson.getLastName());
			statement.setString(3, currentPerson.getEmail());
			statement.setObject(4, currentPerson.getPersonId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении данных пользователя с ID {}: {}", currentPerson.getPersonId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public boolean checkUpdateEmail(String email, UUID excludeId) {
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
			log.error("Ошибка при проверки введенного email при обновлении '{}': {}", email, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void updatePassword(Person currentPerson) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_BY_ID_PERSON)) {
			statement.setString(1, currentPerson.getPassword());
			statement.setObject(2, currentPerson.getPersonId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении пароля пользователя с ID {}: {}", currentPerson.getPersonId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID currentPersonId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_PERSON)) {
			statement.setObject(1, currentPersonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при удалении данных пользователя с  ID {}: {}", currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}