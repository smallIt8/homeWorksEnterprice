package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.util.AppUtil;
import org.example.util.PasswordUtil;
import org.example.util.constant.RegexConstant;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.MenuConstant.*;

@Slf4j
@RequiredArgsConstructor

public class PersonServiceImpl implements PersonService {
	private final PersonRepository personRepository;
	private static final Scanner SCANNER = new Scanner(System.in);
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String family;

	@Override
	public Optional<Person> entry() {
		return AppUtil.loopIterationWithReturnAndExit((count) -> {
			log.info("Авторизация пользователя");
			System.out.print(ENTER_USERNAME);
			String userName = SCANNER.nextLine().toLowerCase();
			System.out.print(ENTER_PASSWORD);
			String inputPassword = SCANNER.nextLine();
			Optional<Person> person = personRepository.entry(userName);
			if (person.isPresent()) {
				Person p = person.get();
				if (PasswordUtil.checkPassword(inputPassword, p.getPassword())) {
					log.info("Пользователь '{}' успешно вошёл в систему", userName);
					System.out.println(WELCOME_MESSAGE + userName);
					return Optional.of(new Person(p.getPersonId()));
				}
			}
			if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
				System.out.println(ERROR_ENTER_USER_NAME_OR_PASSWORD_MESSAGE);
			} else {
				log.error("Ошибка входа пользователя '{}' - неверное имя пользователя или пароль для", userName);
			}
			return Optional.empty();
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	@Override
	public void create() {
		log.info("Регистрация нового пользователя");
		System.out.println(REGISTRATION_MESSAGE);
		createUserName();
		createPassword();
		createFirstName();
		createLastName();
		createEmail();
		Person person = new Person(
				UUID.randomUUID(),
				userName.toLowerCase(),
				password,
				firstName.toUpperCase(),
				lastName.toUpperCase(),
				email.toLowerCase()
		);
		try {
			personRepository.create(person);
			log.info("Пользователь с ID: '{}' успешно создан", person.getPersonId());
			System.out.println(REGISTERED_MESSAGE);
		} catch (RuntimeException e) {
			log.error("Ошибка при создании пользователя: '{}'", e.getMessage(), e);
			throw e;
		}
	}

	private void createUserName() {
		AppUtil.loopIterationAndExit((count) -> {
			log.info("Создание логина пользователя");
			System.out.print(ENTER_USERNAME);
			userName = SCANNER.nextLine();
			if (userName.matches(RegexConstant.USERNAME_REGEX)) {
				if (!personRepository.checkUserName(userName)) {
					log.info("Логин пользователя '{}' успешно создан", userName);
					return true;
				} else {
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.println(ERROR_CREATION_USER_NAME_MESSAGE);
						log.warn("Пользователь с таким логином уже существует: '{}'", userName);
					} else {
						log.error("Ошибка при проверке логина: '{}'", userName);
					}
				}
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_USER_NAME_MESSAGE);
				} else {
					log.error("Ошибка при создании логина пользователя: '{}'", userName);
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	private void createPassword() {
		AppUtil.loopIterationAndExit((count) -> {
			log.info("Создание пароля пользователя");
			System.out.print(ENTER_PASSWORD);
			String inputPassword = SCANNER.nextLine();
			if (inputPassword.matches(RegexConstant.PASSWORD_REGEX)) {
				password = PasswordUtil.hashPassword(inputPassword);
				log.info("Пароль пользователя успешно создан и хэширован");
				return true;
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_PASSWORD_MESSAGE);
				} else {
					log.error("Ошибка при создании пароля пользователя");
				}
				return false;
			}
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	private void createFirstName() {
		AppUtil.loopIterationAndExit(count -> {
			log.info("Создание имени пользователя");
			System.out.print(ENTER_FIRST_NAME);
			firstName = SCANNER.nextLine();
			if (firstName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
				log.info("Имя пользователя успешно создано");
				return true;
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_FIRST_NAME_MESSAGE);
				} else {
					log.error("Ошибка при создании имени пользователя: '{}'", firstName);
				}
				return false;
			}
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	private void createLastName() {
		AppUtil.loopIterationAndExit(count -> {
			log.info("Создание фамилии пользователя");
			System.out.print(ENTER_LAST_NAME);
			lastName = SCANNER.nextLine();
			if (lastName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
				log.info("Фамилия пользователя успешно создана");
				return true;
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_LAST_NAME_MESSAGE);
				} else {
					log.error("Ошибка при создании фамилии пользователя: '{}'", lastName);
				}
				return false;
			}
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	private void createEmail() {
		AppUtil.loopIterationAndExit(count -> {
			log.info("Создание эмейл пользователя");
			System.out.print(ENTER_EMAIL);
			email = SCANNER.nextLine();
			if (email.matches(RegexConstant.EMAIL_REGEX)) {
				if (!personRepository.checkEmail(email)) {
					log.info("Эмейл '{}' пользователя успешно создан", email);
					return true;
				} else {
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
						log.warn("Пользователь с таким эмейл уже существует: '{}'", email);
					} else {
						log.error("Ошибка при проверке эмейл: '{}'", email);
					}
				}
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
				} else {
					log.error("Ошибка при создании эмейл: '{}'", email);
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
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

	@Override
	public Optional<Person> update(UUID currentPerson) {
		log.info("Обновление данных текущего пользователя по ID: '{}'", currentPerson);
		Optional<Person> person = getById(currentPerson);
		if (person.isEmpty()) {
			log.warn("Не удалось обновить данные - пользователь с ID: '{}' не найден", currentPerson);
			return Optional.empty();
		}
		Person personUpdate = person.get();
		System.out.println(UPDATE_PERSON_MESSAGE + personUpdate.getFirstName() + " " + personUpdate.getLastName());
		createFirstName();
		createLastName();
		updateEmail(currentPerson);
		personUpdate.setFirstName(firstName.toUpperCase());
		personUpdate.setLastName(lastName.toUpperCase());
		personUpdate.setEmail(email.toLowerCase());
		try {
			personRepository.update(personUpdate);
			log.info("Данные текущего пользователя по ID '{}' успешно обновлены", personUpdate.getPersonId());
			System.out.println(UPDATED_MESSAGE);
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении данных текущего пользователя по ID '{}': '{}'", personUpdate.getPersonId(), e.getMessage(), e);
			throw e;
		}
		return Optional.of(personUpdate);
	}

	private void updateEmail(UUID currentPerson) {
		AppUtil.loopIterationAndExit(count -> {
			log.info("Обновление email текущего пользователя");
			System.out.print(ENTER_EMAIL);
			email = SCANNER.nextLine();
			if (email.matches(RegexConstant.EMAIL_REGEX)) {
				if (!personRepository.checkUpdateEmail(email, currentPerson)) {
					log.info("Эмейл '{}' пользователя успешно обновлен", email);
					return true;
				} else {
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
						log.warn("Email уже назначен другому пользователю: {}", email);
					} else {
						log.error("Ошибка при проверке обновляемого эмейл: '{}'", email);
					}
				}
			} else {
				if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
					System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
				} else {
					log.error("Ошибка при обновлении эмейл: '{}'", email);
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	@Override
	public Optional<Person> updatePassword(UUID currentPerson) {
		log.info("Обновление пароля текущего пользователя ");
		Optional<Person> person = getById(currentPerson);
		if (person.isEmpty()) {
			log.warn("Не удалось обновить пароль — пользователь с ID: '{}' не найден", currentPerson);
			return Optional.empty();
		}
		Person personUpdate = person.get();
		System.out.println(
				UPDATE_PERSON_PASSWORD_MESSAGE + personUpdate.getFirstName() + " " + personUpdate.getLastName());
		createPassword();
		personUpdate.setPassword(password);
		try {
			personRepository.updatePassword(personUpdate);
			log.info("Пароль текущего пользователя по ID '{}' успешно обновлен", personUpdate.getPersonId());
			System.out.println(UPDATED_PASSWORD_MESSAGE);
		} catch (RuntimeException e) {
			log.error("Ошибка при обновлении пароля текущего пользователя по ID '{}': '{}'", personUpdate.getPersonId(), e.getMessage(), e);
			throw e;
		}
		return Optional.of(personUpdate);
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