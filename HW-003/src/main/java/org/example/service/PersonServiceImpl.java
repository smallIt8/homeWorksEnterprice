package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.util.AppUtil;
import org.example.util.constant.RegexConstant;

import java.math.BigDecimal;
import java.util.*;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.MenuPersonConstant.*;

@Slf4j
@RequiredArgsConstructor

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private static final Scanner SCANNER = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;
    private String department;

    @Override
    public void entry() {
    }

    @Override
    public void create() {
        log.info("Добавление нового сотрудника");
        System.out.println(ADDING_MESSAGE);
        createFirstName();
        createLastName();
        createEmail();
        createSalary();
        createDepartment();
        Person person = new Person(
                UUID.randomUUID(),
                firstName.toUpperCase(),
                lastName.toUpperCase(),
                email.toLowerCase(),
                salary,
                department);
        try {
            personRepository.create(person);
            log.info("Сотрудник успешно создан с ID: {}", person.getPersonId());
            System.out.println(ADDED_MESSAGE);
        } catch (RuntimeException e) {
            log.error("Ошибка при создании сотрудника: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void createBatch() {
        log.info("Запуск пакетного добавления сотрудников");
        System.out.println(ADDING_BATCH_MESSAGE);
        List<Person> persons = new ArrayList<>();
        while (true) {
            System.out.println(ADDING_MESSAGE + (persons.size() + 1));
            createFirstName();
            createLastName();
            createEmail();
            createSalary();
            createDepartment();
            Person person = new Person(
                    UUID.randomUUID(),
                    firstName.toUpperCase(),
                    lastName.toUpperCase(),
                    email.toLowerCase(),
                    salary,
                    department
            );
            persons.add(person);
            log.debug("Добавлен сотрудник в пакет: {}", person);

            for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
                System.out.print(ADDING_PERSON);
                String answer = SCANNER.nextLine().toUpperCase();
                log.debug("Ввод для продолжения пакетного добавления: '{}'", answer);
                if (answer.matches(RegexConstant.YES_OR_NO_REGEX)) {
                    if (answer.equals("Y")) {
                        log.info("Выбран ввод следующего сотрудников");
                        break;
                    } else if (answer.equals("N")) {
                        log.info("Не выбран ввод следующего сотрудника");
                        try {
                            personRepository.createBatch(persons);
                            log.info("Пакет сотрудников успешно добавлен. Всего: {}", persons.size());
                            System.out.println(ADDED_PERSONS_MESSAGE);
                        } catch (RuntimeException e) {
                            log.error("Ошибка при пакетном добавлении сотрудников: {}", e.getMessage(), e);
                            throw e;
                        }
                        return;
                    }
                } else if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                    System.out.println(ERROR_ENTER_YES_OR_NO_MESSAGE);
                    log.warn("Неверный ввод ответа: {}", answer);
                } else {
                    log.error("Превышено количество попыток ввода Y/N");
                    AppUtil.exitByFromAttempt();
                }
            }
        }
    }

    private void createFirstName() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_FIRST_NAME);
            firstName = SCANNER.nextLine();
            log.debug("Ввод имени: {}", firstName);
            if (firstName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_FIRST_NAME_MESSAGE);
                log.warn("Неверный ввод имени: {}", firstName);
            } else {
                log.error("Превышено количество попыток ввода имени");
                AppUtil.exitByFromAttempt();
            }
        }
    }

    private void createLastName() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_LAST_NAME);
            lastName = SCANNER.nextLine();
            log.debug("Ввод фамилии: {}", lastName);
            if (lastName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_LAST_NAME_MESSAGE);
                log.warn("Неверный ввод фамилии: {}", lastName);
            } else {
                log.error("Превышено количество попыток ввода фамилии");
                AppUtil.exitByFromAttempt();
            }
        }
    }

    private void createEmail() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_EMAIL);
            email = SCANNER.nextLine();
            log.debug("Ввод email: {}", email);
            if (email.matches(RegexConstant.EMAIL_REGEX)) {
                try {
                    if (personRepository.checkEmail(email)) {
                        if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                            System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
                            log.warn("Email уже существует: {}", email);
                        } else {
                            log.error("Превышено количество попыток ввода email");
                            AppUtil.exitByFromAttempt();
                        }
                    } else {
                        return;
                    }
                } catch (RuntimeException e) {
                    log.error("Ошибка при проверке email в базе: {}", e.getMessage(), e);
                    throw e;
                }
            } else {
                if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                    System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
                    log.warn("Неверный формат email: {}", email);
                } else {
                    log.error("Превышено количество попыток ввода email по формату");
                    AppUtil.exitByFromAttempt();
                }
            }
        }
    }

    private void createSalary() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_SALARY);
            String input = SCANNER.nextLine();
            log.debug("Ввод зарплаты: {}", input);
            if (input.matches(RegexConstant.SALARY_REGEX)) {
                salary = new BigDecimal(input);
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_SALARY_MESSAGE);
                log.warn("Неверный формат зарплаты: {}", input);
            } else {
                log.error("Превышено количество попыток ввода зарплаты");
                AppUtil.exitByFromAttempt();
            }
        }
    }

    private void createDepartment() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_DEPARTMENT);
            department = SCANNER.nextLine();
            log.debug("Ввод отдела: {}", department);
            if (department.matches(RegexConstant.DEPARTMENT_REGEX)) {
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_DEPARTMENT_MESSAGE);
                log.warn("Неверный ввод отдела: {}", department);
            } else {
                log.error("Превышено количество попыток ввода отдела");
                AppUtil.exitByFromAttempt();
            }
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = personRepository.getAll();
        if (persons.isEmpty()) {
            log.warn("Список сотрудников пуст");
            System.out.println(EMPTY_LIST_PERSON_MESSAGE);
        } else {
            log.info("Получено данных {} сотрудников", persons.size());
            System.out.println(LIST_PERSON_MESSAGE);
            AppUtil.printNumberedList(persons);
        }
        return persons;
    }

    @Override
    public void getAllByCreateDate() {
        List<Person> persons = personRepository.getAllByCreateDate();
        if (persons.isEmpty()) {
            log.warn("Список сотрудников пуст (сортировка по дате приёма)");
            System.out.println(EMPTY_LIST_PERSON_MESSAGE);
        } else {
            log.info("Получено {} сотрудников, отсортированных по дате приёма", persons.size());
            System.out.println(LIST_PERSON_BY_CREATE_DATE_MESSAGE);
            AppUtil.printNumberedList(persons);
        }
    }

    @Override
    public void getByLastName(String lastName) {
        List<Person> persons = personRepository.getByLastName(lastName);
        if (persons.isEmpty()) {
            log.warn("Сотрудники с такой фамилией не найдены");
            System.out.println(EMPTY_LIST_LAST_NAME_SEARCH_PERSON_MESSAGE);
        } else {
            log.info("Получено {} сотрудников с введенной фамилией {}", persons.size(), lastName);
            System.out.println(LIST_PERSON_LAST_NAME_SEARCH_MESSAGE + lastName);
            AppUtil.printNumberedList(persons);
        }
    }

    @Override
    public void getAllBySalary() {
        List<Person> persons = personRepository.getAllBySalary();
        if (persons.isEmpty()) {
            log.warn("Список сотрудников пуст (сортировка по зарплате)");
            System.out.println(EMPTY_LIST_PERSON_MESSAGE);
        } else {
            log.info("Получено {} сотрудников, отсортированных по зарплате", persons.size());
            System.out.println(LIST_PERSON_BY_SALARY_MESSAGE);
            AppUtil.printNumberedList(persons);
        }
    }

    @Override
    public Optional<Person> getById(UUID personId) {
        log.info("Получение данных сотрудника по ID: {}", personId);
        Optional<Person> person = personRepository.getById(personId);
        person.ifPresentOrElse(
                System.out::println,
                () -> System.out.println(PERSON_NOT_FOUND_MESSAGE)
        );
        return person;
    }

    @Override
    public Optional<Person> updateById(UUID personId) {
        log.info("Обновление данных сотрудника с ID: {}", personId);
        Optional<Person> person = getById(personId);
        if (person.isEmpty()) {
            log.warn("Сотрудник для обновления не найден с ID: {}", personId);
            return Optional.empty();
        }
        Person personUpdate = person.get();
        System.out.println(UPDATE_PERSON_MESSAGE + personUpdate.getPersonId());
        createFirstName();
        createLastName();
        createEmail(personId);
        createSalary();
        createDepartment();
        personUpdate.setFirstName(firstName.toUpperCase());
        personUpdate.setLastName(lastName.toUpperCase());
        personUpdate.setEmail(email.toLowerCase());
        personUpdate.setSalary(salary);
        personUpdate.setDepartment(department);
        try {
            personRepository.updateById(personUpdate);
            log.info("Данные сотрудника с ID {} успешно обновлены", personUpdate.getPersonId());
            System.out.println(UPDATED_MESSAGE);
        } catch (RuntimeException e) {
            log.error("Ошибка при обновлении данных сотрудника с ID {}: {}", personUpdate.getPersonId(), e.getMessage(), e);
            throw e;
        }
        return Optional.of(personUpdate);
    }

    private void createEmail(UUID currentPersonId) {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_EMAIL);
            email = SCANNER.nextLine();
            log.debug("Ввод email для обновления: {}", email);

            if (email.matches(RegexConstant.EMAIL_REGEX)) {
                try {
                    if (personRepository.checkEmail(email, currentPersonId)) {
                        if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                            System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
                            log.warn("Email уже назначен другому пользователю: {}", email);
                        } else {
                            log.error("Превышено количество попыток ввода email при обновлении");
                            AppUtil.exitByFromAttempt();
                        }
                    } else {
                        return;
                    }
                } catch (RuntimeException e) {
                    log.error("Ошибка при проверке email в базе: {}", e.getMessage(), e);
                    throw e;
                }
            } else {
                if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                    System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
                    log.warn("Неверный формат email при обновлении: {}", email);
                } else {
                    log.error("Превышено количество попыток ввода email при обновлении по формату");
                    AppUtil.exitByFromAttempt();
                }
            }
        }
    }

    @Override
    public void delete(UUID personId) {
        log.info("Удаление данных сотрудника с ID: {}", personId);
        getById(personId).ifPresent(person -> {
            try {
                personRepository.delete(personId);
                log.info("Данные сотрудника с ID {} удалёны", person.getPersonId());
                System.out.println(DELETED_MESSAGE + person.getPersonId());
            } catch (RuntimeException e) {
                log.error("Ошибка при удалении данных сотрудника с ID {}: {}", person.getPersonId(), e.getMessage(), e);
                throw e;
            }
        });
    }
}