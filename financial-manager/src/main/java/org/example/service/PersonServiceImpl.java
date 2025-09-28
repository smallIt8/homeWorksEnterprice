package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.mapper.PersonMapper;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.util.constant.RegexConstant.*;
import static org.example.util.PasswordHashUtil.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;
	private final PersonMapper personMapper;

	@Override
	public Optional<PersonDto> getPersonByUserName(PersonDto personDto) {
		log.debug("Авторизация пользователя");
		final var personModel = personMapper.mapDtoToModel(personDto);

		Optional<Person> personOpt = personRepository.getPersonByUserName(personModel.getUserName().toLowerCase());

		if (personOpt.isPresent()) {
			var person = personOpt.get();
			if (checkPassword(personModel.getPassword(), person.getPassword())) {
				log.info("Пользователь '{}' успешно вошёл в систему", personModel.getUserName());
				return Optional.of(personMapper.mapModelToDto(person));
			}
		}
		log.warn("Ошибка входа пользователя '{}' - неверное имя пользователя или пароль", personModel.getUserName());
		throw new IllegalArgumentException(ERROR_ENTER_USER_NAME_OR_PASSWORD_MESSAGE);
	}

	@Override
	public void create(PersonDto personDto) {
		final var person = personMapper.mapDtoToModel(personDto);

		log.debug("Регистрация нового пользователя");

		var userName = validateUserName(person.getUserName());
		var password = validatePassword(person.getPassword());
		var firstName = validateFirstName(person.getFirstName());
		var lastName = validateLastName(person.getLastName());
		var email = validateEmail(person.getEmail());

		var inputPerson = Person.builder()
				.personId(UUID.randomUUID())
				.userName(userName.toLowerCase())
				.password(password)
				.firstName(firstName)
				.lastName(lastName)
				.email(email.toLowerCase())
				.build();

		log.debug("Создана подготовленная модель создаваемого пользователя: '{}'", inputPerson);
		try {
			personRepository.create(inputPerson);
			log.info("Пользователь с ID: '{}' успешно создан", inputPerson.getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании пользователя: '{}'", e.getMessage(), e);
			throw e;
		}
	}

	private String validateUserName(String userName) {
		log.debug("Создание логина пользователя");
		if (userName.matches(USERNAME_REGEX)) {
			if (!personRepository.existsByUserName(userName)) {
				log.info("Логин пользователя '{}' успешно создан", userName);
				return userName;
			} else {
				log.warn("Пользователь с таким логином уже существует: '{}'", userName);
				throw new IllegalArgumentException(ERROR_CREATION_USER_NAME_MESSAGE);
			}
		} else {
			log.warn("Неверный формат логина: '{}'", userName);
			throw new IllegalArgumentException(WARNING_ENTER_USER_NAME_MESSAGE);
		}
	}

	private String validatePassword(String password) {
		log.debug("Создание пароля пользователя");
		if (password.matches(PASSWORD_REGEX)) {
			password = hashPassword(password);
			log.info("Пароль пользователя успешно создан и хэширован");
			return password;
		} else {
			log.warn("Неверный формат пароля");
			throw new IllegalArgumentException(WARNING_ENTER_PASSWORD_MESSAGE);
		}
	}

	private String validateFirstName(String firstName) {
		log.debug("Создание имени пользователя");
		if (firstName.matches(FIRST_AND_LAST_NAME_REGEX)) {
			log.info("Имя пользователя успешно создано: '{}'", firstName);
			return firstName;
		} else {
			log.warn("Неверный формат имени: '{}'", firstName);
			throw new IllegalArgumentException(WARNING_ENTER_FIRST_NAME_MESSAGE);
		}
	}

	private String validateLastName(String lastName) {
		log.debug("Создание фамилии пользователя");
		if (lastName.matches(FIRST_AND_LAST_NAME_REGEX)) {
			log.info("Фамилия пользователя успешно создана: '{}'", lastName);
			return lastName;
		} else {
			log.warn("Неверный формат фамилии: '{}'", lastName);
			throw new IllegalArgumentException(WARNING_ENTER_LAST_NAME_MESSAGE);
		}
	}

	private String validateEmail(String email) {
		log.debug("Создание эмейл пользователя");
		if (email.matches(EMAIL_REGEX)) {
			if (!personRepository.existsByEmail(email)) {
				log.info("Эмейл '{}' пользователя успешно создан", email);
				return email;
			} else {
				log.warn("Пользователь с таким эмейл уже существует: '{}'", email);
				throw new IllegalArgumentException(ERROR_CREATION_EMAIL_MESSAGE);
			}
		} else {
			log.warn("Неверный формат эмейл: '{}'", email);
			throw new IllegalArgumentException(WARNING_ENTER_EMAIL_MESSAGE);
		}
	}

	@Override
	public Optional<Person> findById(UUID personId) {
		log.debug("Получение данных пользователя по ID: '{}'", personId);
		try {
			Optional<Person> personOpt = personRepository.findById(personId);
			if (personOpt.isEmpty()) {
				log.warn("Пользователь с ID: '{}' не найден", personId);
				throw new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
			}
			log.info("Данные пользователя по ID: '{}' успешно получены", personId);
			return personOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных пользователя по ID '{}': '{}'", personId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<PersonDto> update(PersonDto currentPersonDto) {
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Обновление данных текущего пользователя по ID: '{}'", person.getPersonId());
		var personUpdate = findById(
				person.getPersonId()).orElseThrow(() -> {
					log.warn("Не удалось обновить данные — пользователь с ID: '{}' не найден", person.getPersonId());
					return new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
		});

		log.debug("Обновление данных текущего пользователя с ID: '{}'", person.getPersonId());
		var firstName = validateFirstName(person.getFirstName());
		var lastName = validateLastName(person.getLastName());
		var email = updateEmail(person.getEmail(), personUpdate.getPersonId());

		personUpdate.setFirstName(firstName);
		personUpdate.setLastName(lastName);
		personUpdate.setEmail(email.toLowerCase());

		log.debug("Создана подготовленная модель обновляемого пользователя с ID: '{}'", personUpdate.getPersonId());
		try {
			personRepository.update(personUpdate);
			log.info("Данные текущего пользователя по ID '{}' успешно обновлены", personUpdate.getPersonId());
			return Optional.of(personMapper.mapModelToDto(personUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении данных текущего пользователя по ID '{}': '{}'",
					  personUpdate.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	public String updateEmail(String email, UUID personId) {
		log.debug("Обновление email '{}' текущего пользователя по ID: '{}'", email, personId);
		var personUpdateEmail = findById(personId).orElseThrow(() -> {
			log.warn("Не удалось обновить email, пользователь с ID: '{}' не найден", personId);
			return new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
		});

		try {
			if (email.matches(EMAIL_REGEX)) {
				if (!personRepository.updateEmailExistsByExclude(email, personUpdateEmail.getPersonId())) {
					log.info("Email '{}' пользователя успешно обновлен", email);
					return email;
				} else {
					log.warn("Email уже назначен другому пользователю: {}", email);
					throw new IllegalArgumentException(ERROR_CREATION_EMAIL_MESSAGE);
				}
			} else {
				log.warn("Неверный формат обновляемого email: '{}'", email);
				throw new IllegalArgumentException(WARNING_ENTER_EMAIL_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Ошибка при обновлении email: '{}' '{}'", email, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<PersonDto> updatePassword(PersonDto currentPersonDto) {
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Обновление пароля текущего пользователя по ID: '{}'", person.getPersonId());
		var personUpdatePassword = findById(person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось обновить пароль — пользователь с ID: '{}' не найден", person.getPersonId());
			return new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
		});

		var password = validatePassword(person.getPassword());

		personUpdatePassword.setPassword(password);

		log.debug("Создана подготовленная модель обновляемого password пользователя с ID: '{}'", personUpdatePassword.getPersonId());
		try {
			personRepository.updatePassword(personUpdatePassword);
			log.info("Пароль текущего пользователя по ID '{}' успешно обновлен", personUpdatePassword.getPersonId());
			return Optional.of(personMapper.mapModelToDto(personUpdatePassword));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении пароля текущего пользователя по ID '{}': '{}'",
					  personUpdatePassword.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void delete(PersonDto currentPersonDto) {
		final var person = personMapper.mapDtoToModel(currentPersonDto);

		log.debug("Удаление текущего пользователя с ID: '{}'", person.getPersonId());
		var personToDelete = findById(person.getPersonId()).orElseThrow(() -> {
			log.warn("Не удалось удалить данные — пользователь с ID: '{}' не найден", person.getPersonId());
			return new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
		});

		try {
			personRepository.delete(personToDelete.getPersonId());
			log.info("Данные пользователя с ID '{}' удалены", personToDelete.getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных пользователя с ID '{}': '{}'",
					  personToDelete.getPersonId(),
					  e.getMessage(), e);
			throw e;
		}
	}
}
