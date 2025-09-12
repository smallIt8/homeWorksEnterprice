package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.PersonMapper;
import org.example.dto.PersonDto;
import org.example.model.Family;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.util.PasswordUtil;
import org.example.util.constant.RegexConstant;

import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.MenuConstant.*;

@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;

	@Override
	public Optional<PersonDto> entry(PersonDto personDto) {
		log.info("Авторизация пользователя");
		Person personModel = PersonMapper.dtoToModel(personDto);
		Optional<Person> personOpt = personRepository.entry(personModel.getUserName().toLowerCase());
		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			if (PasswordUtil.checkPassword(personModel.getPassword(), person.getPassword())) {
				log.info("Пользователь '{}' успешно вошёл в систему", personModel.getUserName());
				return Optional.of(PersonMapper.modelToDto(person));
			}
		}
		log.warn("Ошибка входа пользователя '{}' - неверное имя пользователя или пароль", personModel.getUserName());
		return Optional.empty();
	}

	@Override
	public void create(PersonDto personDto) {
		log.info("Регистрация нового пользователя");
		Person personModel = PersonMapper.dtoToModel(personDto);

		String userName = createUserName(personModel.getUserName());
		String password = createPassword(personModel.getPassword());
		String firstName = createFirstName(personModel.getFirstName());
		String lastName = createLastName(personModel.getLastName());
		String email = createEmail(personModel.getEmail());

		Person inputPerson = new Person(
				UUID.randomUUID(),
				userName.toLowerCase(),
				password,
				firstName.toUpperCase(),
				lastName.toUpperCase(),
				email.toLowerCase());
		log.info("Создана подготовленная модель создаваемого пользователя: '{}'", inputPerson);
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
				log.warn("Пользователь с таким логином уже существует: '{}'", userName);
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
	public Optional<Person> getById(UUID currentPerson) {
		try {
			log.info("Получение данных сотрудника по ID: '{}'", currentPerson);
			Optional<Person> person = personRepository.getById(currentPerson);
			person.ifPresentOrElse(
					System.out::println,
					() -> {
						log.warn("Пользователь с ID: '{}' не найден", currentPerson);
						System.out.println(PERSON_NOT_FOUND_MESSAGE);
					}
			);
			return person;
		} catch (RuntimeException e) {
			log.error("Ошибка при получении данных пользователя по ID '{}': '{}'", currentPerson, e.getMessage(), e);
			throw e;
		}
	}

	// TODO: перенести на веб
	@Override
	public Optional<Person> update(PersonDto currentPerson) {
//		log.info("Обновление данных текущего пользователя по ID: '{}'", currentPerson);
//		Optional<Person> person = getById(currentPerson.getPersonId());
//		if (person.isEmpty()) {
//			log.warn("Не удалось обновить данные - пользователь с ID: '{}' не найден", currentPerson);
//			return Optional.empty();
//		}
//		Person personUpdate = person.get();
//		System.out.println(UPDATE_PERSON_MESSAGE + personUpdate.getFirstName() + " " + personUpdate.getLastName());
//		createFirstName();
//		createLastName();
//		updateEmail(currentPerson.getPersonId());
//		personUpdate.setFirstName(firstName.toUpperCase());
//		personUpdate.setLastName(lastName.toUpperCase());
//		personUpdate.setEmail(email.toLowerCase());
//		try {
//			personRepository.update(personUpdate);
//			log.info("Данные текущего пользователя по ID '{}' успешно обновлены", personUpdate.getPersonId());
//			System.out.println(UPDATED_PERSON_MESSAGE);
//		} catch (RuntimeException e) {
//			log.error("Ошибка при обновлении данных текущего пользователя по ID '{}': '{}'", personUpdate.getPersonId(), e.getMessage(), e);
//			throw e;
//		}
		return Optional.of(new Person());
	}

	// TODO: перенести на веб
	private void updateEmail(UUID currentPerson) {
//		AppUtil.loopIterationAndExit(count -> {
//			log.info("Обновление email текущего пользователя");
//			System.out.print(ENTER_EMAIL);
//			email = SCANNER.nextLine();
//			if (email.matches(RegexConstant.EMAIL_REGEX)) {
//				if (!personRepository.checkUpdateEmail(email, currentPerson)) {
//					log.info("Эмейл '{}' пользователя успешно обновлен", email);
//					return true;
//				} else {
//					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
//						System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
//						log.warn("Email уже назначен другому пользователю: {}", email);
//					} else {
//						log.error("Ошибка при проверке обновляемого эмейл: '{}'", email);
//					}
//				}
//			} else {
//				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
//					System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
//				} else {
//					log.error("Ошибка при обновлении эмейл: '{}'", email);
//				}
//			}
//			return false;
//		}, AppUtil.MAX_ITERATION_LOOP);
	}

	// TODO: перенести на веб
	@Override
	public Optional<Person> updatePassword(PersonDto currentPerson) {
//		log.info("Обновление пароля текущего пользователя ");
//		Optional<Person> person = getById(currentPerson.getPersonId());
//		if (person.isEmpty()) {
//			log.warn("Не удалось обновить пароль — пользователь с ID: '{}' не найден", currentPerson);
//			return Optional.empty();
//		}
//		Person personUpdatePassword = person.get();
//		System.out.println(
//				UPDATE_PERSON_PASSWORD_MESSAGE + personUpdatePassword.getFirstName() + " " +
//						personUpdatePassword.getLastName());
//		createPassword();
//		personUpdatePassword.setPassword(password);
//		try {
//			personRepository.updatePassword(personUpdatePassword);
//			log.info("Пароль текущего пользователя по ID '{}' успешно обновлен", personUpdatePassword.getPersonId());
//			System.out.println(UPDATED_PERSON_PASSWORD_MESSAGE);
//		} catch (RuntimeException e) {
//			log.error("Ошибка при обновлении пароля текущего пользователя по ID '{}': '{}'", personUpdatePassword.getPersonId(), e.getMessage(), e);
//			throw e;
//		}
		return Optional.of(new Person());
	}

	@Override
	public void updateFamily(Person person, Family family) {

	}

	@Override
	public void delete(UUID currentPerson) {
		log.info("Удаление текущего пользователя с ID: '{}'", currentPerson);
		Optional<Person> person = getById(currentPerson);
		if (person.isEmpty()) {
			log.warn("Не удалось удалить данные — пользователь с ID: '{}' - не найден", currentPerson);
			return;
		}
		try {
			personRepository.delete(currentPerson);
			log.info("Данные пользователя с ID '{}' удалены", currentPerson);
			System.out.println(DELETED_MESSAGE + currentPerson);
		} catch (RuntimeException e) {
			log.error("Ошибка при удалении данных пользователя с ID '{}': '{}'", currentPerson, e.getMessage(), e);
			throw e;
		}
	}
}