package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.dto.PersonDto;
import org.example.model.Family;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.util.PasswordUtil;
import org.example.util.constant.RegexConstant;

import java.util.Optional;
import java.util.UUID;

import static org.example.mapper.PersonMapper.*;
import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.MenuConstant.*;

@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;

	@Override
	public Optional<PersonDto> entry(PersonDto personDto) {
		log.info("Авторизация пользователя");
		Person personModel = dtoToModel(personDto);
		Optional<Person> personOpt = personRepository.entry(personModel.getUserName().toLowerCase());
		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			if (PasswordUtil.checkPassword(personModel.getPassword(), person.getPassword())) {
				log.info("Пользователь '{}' успешно вошёл в систему",
						 personModel.getUserName());
				return Optional.of(modelToDto(person));
			}
		}
		log.warn("Ошибка входа пользователя '{}' - неверное имя пользователя или пароль",
				 personModel.getUserName());
		return Optional.empty();
	}

	@Override
	public void create(PersonDto personDto) {
		log.info("Регистрация нового пользователя");
		Person person = dtoToModel(personDto);

		String userName = createUserName(person.getUserName());
		String password = createPassword(person.getPassword());
		String firstName = createFirstName(person.getFirstName());
		String lastName = createLastName(person.getLastName());
		String email = createEmail(person.getEmail());

		Person inputPerson = Person.builder()
				.personId(UUID.randomUUID())
				.userName(userName.toLowerCase())
				.password(password)
				.firstName(firstName.toUpperCase())
				.lastName(lastName.toUpperCase())
				.email(email.toLowerCase())
				.build();
		log.info("Создана подготовленная модель создаваемого пользователя: '{}'",
				 inputPerson);
		try {
			personRepository.create(inputPerson);
			log.info("Пользователь с ID: '{}' успешно создан", inputPerson.getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при создании пользователя: '{}'", e.getMessage(), e);
			throw e;
		}
	}

	private String createUserName(String userName) {
		log.info("Создание логина пользователя");
		if (userName.matches(RegexConstant.USERNAME_REGEX)) {
			if (!personRepository.checkUserName(userName)) {
				log.info("Логин пользователя '{}' успешно создан", userName);
				return userName;
			} else {
				log.warn("Пользователь с таким логином уже существует: '{}'",
						 userName);
				throw new IllegalArgumentException(ERROR_CREATION_USER_NAME_MESSAGE);
			}
		} else {
			log.error("Неверный формат логина: '{}'", userName);
			throw new IllegalArgumentException(ERROR_ENTER_USER_NAME_MESSAGE);
		}
	}

	private String createPassword(String password) {
		log.info("Создание пароля пользователя");
		if (password.matches(RegexConstant.PASSWORD_REGEX)) {
			password = PasswordUtil.hashPassword(password);
			log.info("Пароль пользователя успешно создан и хэширован");
			return password;
		} else {
			log.error("Неверный формат пароля");
			throw new IllegalArgumentException(ERROR_ENTER_PASSWORD_MESSAGE);
		}
	}

	private String createFirstName(String firstName) {
		log.info("Создание имени пользователя");
		if (firstName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
			log.info("Имя пользователя успешно создано");
			return firstName;
		} else {
			log.error("Неверный формат имени: '{}'", firstName);
			throw new IllegalArgumentException(ERROR_ENTER_FIRST_NAME_MESSAGE);
		}
	}

	private String createLastName(String lastName) {
		log.info("Создание фамилии пользователя");
		if (lastName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
			log.info("Фамилия пользователя успешно создана");
			return lastName;
		} else {
			log.error("Неверный формат фамилии: '{}'", lastName);
			throw new IllegalArgumentException(ERROR_ENTER_LAST_NAME_MESSAGE);
		}
	}

	private String createEmail(String email) {
		log.info("Создание эмейл пользователя");
		if (email.matches(RegexConstant.EMAIL_REGEX)) {
			if (!personRepository.checkEmail(email)) {
				log.info("Эмейл '{}' пользователя успешно создан", email);
				return email;
			} else {
				log.warn("Пользователь с таким эмейл уже существует: '{}'", email);
				throw new IllegalArgumentException(ERROR_CREATION_EMAIL_MESSAGE);
			}
		} else {
			log.error("Неверный формат эмейл: '{}'", email);
			throw new IllegalArgumentException(ERROR_ENTER_EMAIL_MESSAGE);
		}
	}

	@Override
	public Optional<Person> getById(UUID personId) {
		log.info("Получение данных пользователя по ID: '{}'", personId);
		try {
			Optional<Person> personOpt = personRepository.getById(personId);
			if (personOpt.isEmpty()) {
				log.warn("Пользователь с ID: '{}' не найден", personId);
				throw new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
			}
			return personOpt;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных пользователя по ID '{}': '{}'",
					  personId, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<PersonDto> update(PersonDto currentPersonDto) {
		Person person = dtoToModel(currentPersonDto);
		log.info("Обновление данных текущего пользователя по ID: '{}'",
				 person.getPersonId());
		Optional<Person> personOpt = getById(person.getPersonId());
		if (personOpt.isEmpty()) {
			log.warn("Не удалось обновить данные - пользователь с ID: '{}' не найден",
					 person.getPersonId());
			return Optional.empty();
		}

		Person personUpdate = personOpt.get();

		String firstName = createFirstName(person.getFirstName());
		String lastName = createLastName(person.getLastName());
		String email = updateEmail(person.getEmail(), personUpdate.getPersonId());

		personUpdate.setFirstName(firstName.toUpperCase());
		personUpdate.setLastName(lastName.toUpperCase());
		personUpdate.setEmail(email.toLowerCase());

		log.info("Создана подготовленная модель обновляемого пользователя с ID: '{}'",
				 personUpdate.getPersonId());
		try {
			personRepository.update(personUpdate);
			log.info("Данные текущего пользователя по ID '{}' успешно обновлены",
					 personUpdate.getPersonId());
			return Optional.of(modelToDto(personUpdate));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении данных текущего пользователя по ID '{}': '{}'",
					  personUpdate.getPersonId(), e.getMessage(), e);
			throw e;
		}
	}

	public String updateEmail(String email, UUID personId) {
		log.info("Обновление email текущего пользователя по ID: '{}'",
				 personId);
		Optional<Person> personOpt = getById(personId);
		if (personOpt.isEmpty()) {
			log.warn("Не удалось обновить email, пользователь с ID: '{}' не найден",
					 personId);
			throw new IllegalArgumentException(NOT_FOUND_PERSON_MESSAGE);
		}

		Person personUpdateEmail = personOpt.get();

		log.info("Обновление email текущего пользователя");
		try {
			if (email.matches(RegexConstant.EMAIL_REGEX)) {
				if (!personRepository.checkUpdateEmail(email, personUpdateEmail.getPersonId())) {
					log.info("Email '{}' пользователя успешно обновлен", email);
					return email;
				} else {
					log.warn("Email уже назначен другому пользователю: {}", email);
					throw new IllegalArgumentException(ERROR_CREATION_EMAIL_MESSAGE);
				}
			} else {
				log.error("Неверный формат обновляемого email: '{}'", email);
				throw new IllegalArgumentException(ERROR_ENTER_EMAIL_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Ошибка при обновлении email: '{}' '{}'", email, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Optional<PersonDto> updatePassword(PersonDto currentPersonDto) {
		Person person = dtoToModel(currentPersonDto);
		log.info("Обновление пароля текущего пользователя по ID: '{}'",
				 person.getPersonId());
		Optional<Person> personOpt = getById(person.getPersonId());
		if (personOpt.isEmpty()) {
			log.warn("Не удалось обновить пароль — пользователь с ID: '{}' не найден",
					 person.getPersonId());
			return Optional.empty();
		}
		Person personUpdatePassword = personOpt.get();

		String hashedPassword = createPassword(person.getPassword());

		personUpdatePassword.setPassword(hashedPassword);

		log.info("Создана подготовленная модель обновляемого password пользователя с ID: '{}'",
				 personUpdatePassword.getPersonId());
		try {
			personRepository.updatePassword(personUpdatePassword);
			log.info("Пароль текущего пользователя по ID '{}' успешно обновлен", personUpdatePassword.getPersonId());
			return Optional.of(modelToDto(personUpdatePassword));
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении пароля текущего пользователя по ID '{}': '{}'", personUpdatePassword.getPersonId(), e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void updateFamily(Person person, Family family) {

	}

	@Override
	public void delete(PersonDto currentPersonDto) {
		Person person = dtoToModel(currentPersonDto);
		log.info("Удаление текущего пользователя с ID: '{}'", person.getPersonId());
		Optional<Person> personOpt = getById(person.getPersonId());
		if (personOpt.isEmpty()) {
			log.warn("Не удалось удалить данные — пользователь с ID: '{}' - не найден", person.getPersonId());
			return;
		}
		try {
			personRepository.delete(personOpt.get().getPersonId());
			log.info("Данные пользователя с ID '{}' удалены", personOpt.get().getPersonId());
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных пользователя с ID '{}': '{}'", personOpt.get().getPersonId(), e.getMessage(), e);
			throw e;
		}
	}
}