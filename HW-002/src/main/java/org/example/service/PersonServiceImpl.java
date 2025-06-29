package org.example.service;

import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.util.AppUtil;
import org.example.util.constant.RegexConstant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.example.util.constant.ExceptionMessage.*;
import static org.example.util.constant.MenuPersonConstant.*;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final Scanner SCANNER = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;
    private String department;

    @Override
    public void create() {
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
        personRepository.create(person);
        System.out.println(ADDED_MESSAGE);
    }

    private void createFirstName() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_FIRST_NAME);
            firstName = SCANNER.nextLine();
            if (firstName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_FIRST_NAME_MESSAGE);
            } else {
                AppUtil.exitByFromAttempt();
            }
        }
    }

    private void createLastName() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_LAST_NAME);
            lastName = SCANNER.nextLine();
            if (lastName.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_LAST_NAME_MESSAGE);
            } else {
                AppUtil.exitByFromAttempt();
            }
        }
    }

    private void createEmail() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_EMAIL);
            email = SCANNER.nextLine();
            try {
                if (email.matches(RegexConstant.EMAIL_REGEX)) {
                    if (personRepository.checkEmail(email)) {
                        if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                            System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
                        } else {
                            AppUtil.exitByFromAttempt();
                        }
                    } else {
                        break;
                    }
                } else {
                    if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                        System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
                    } else {
                        AppUtil.exitByFromAttempt();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void createEmail(UUID currentPersonId) {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_EMAIL);
            email = SCANNER.nextLine();
            try {
                if (email.matches(RegexConstant.EMAIL_REGEX)) {
                    if (personRepository.checkEmail(email, currentPersonId)) {
                        if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                            System.out.println(ERROR_CREATION_EMAIL_MESSAGE);
                        } else {
                            AppUtil.exitByFromAttempt();
                        }
                    } else {
                        break;
                    }
                } else {
                    if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                        System.out.println(ERROR_ENTER_EMAIL_MESSAGE);
                    } else {
                        AppUtil.exitByFromAttempt();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void createSalary() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_SALARY);
            String input = SCANNER.nextLine();
            if (input.matches(RegexConstant.SALARY_REGEX)) {
                salary = new BigDecimal(input);
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_SALARY_MESSAGE);
            } else {
                AppUtil.exitByFromAttempt();
            }
        }
    }

    private void createDepartment() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_DEPARTMENT);
            department = SCANNER.nextLine();
            if (department.matches(RegexConstant.DEPARTMENT_REGEX)) {
                break;
            }
            if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                System.out.println(ERROR_ENTER_DEPARTMENT_MESSAGE);
            } else {
                AppUtil.exitByFromAttempt();
            }
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = personRepository.getAll();
        if (persons.isEmpty()) {
            System.out.println(EMPTY_LIST_PERSON_MESSAGE);
        } else {
            System.out.println(LIST_PERSON_MESSAGE);
            for (int i = 0; i < persons.size(); i++) {
                Person person = persons.get(i);
                String numberOfPerson = AppUtil.colorizeNumber(person.toString(), i + 1);
                System.out.println(numberOfPerson);
            }
        }
        return persons;
    }

    @Override
    public Optional<Person> getById(UUID personId) {
        Optional<Person> person = personRepository.getById(personId);
        person.ifPresentOrElse(
                System.out::println,
                () -> System.out.println(PERSON_NOT_FOUND_MESSAGE)
        );
        return person;
    }

    @Override
    public Optional<Person> updateById(UUID personId) {
        Optional<Person> person = getById(personId);
        if (person.isEmpty()) {
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
        personRepository.updateById(personUpdate);
        System.out.println(UPDATED_MESSAGE);
        return Optional.of(personUpdate);
    }

    @Override
    public void delete(UUID personId) {
        getById(personId).ifPresent(person -> {
            personRepository.delete(personId);
            System.out.println(DELETED_MESSAGE + person.getPersonId());
        });
    }

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}