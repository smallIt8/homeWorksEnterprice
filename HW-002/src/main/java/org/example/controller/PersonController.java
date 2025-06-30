package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.PersonService;
import org.example.util.AppUtil;
import org.example.util.constant.RegexConstant;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.example.util.constant.ExceptionMessageConstant.*;
import static org.example.util.constant.MenuPersonConstant.*;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class PersonController {
    private final PersonService personService;
    private static final Scanner SCANNER = new Scanner(System.in);

    public void welcomePerson() {
        log.info("Запуск главного меню приложения");
        System.out.print(MAIN_MENU);
        AppUtil.loopIterationAndExit((int count) -> {
            try {
                String step = SCANNER.nextLine();
                log.debug("Пользователь ввёл шаг меню: {}", step);
                switch (step) {
                    case STEP_ONE -> personService.create();
                    case STEP_TWO -> personService.createBatch();
                    case STEP_THREE -> personService.getAll();
                    case STEP_FOUR -> personService.getAllBySalary();
                    case STEP_FIVE -> personService.getAllByCreateDate();
                    case STEP_SIX -> checkValidLastNameSearch().ifPresent(personService::getByLastName);
                    case STEP_SEVEN -> checkValidUUID().ifPresent(personService::getById);
                    case STEP_EIGHT -> checkValidUUID().ifPresent(personService::updateById);
                    case STEP_NINE -> checkValidUUID().ifPresent(personService::delete);
                    case STEP_ZERO -> {
                        log.info("Выход из приложения");
                        AppUtil.exit();
                    }
                    default -> {
                        if (count < AppUtil.ITERATION_LOOP_TO_MESSAGE)
                            System.out.print(ERROR_ENTER_MESSAGE);
                    }
                }
                System.out.print(MAIN_MENU);
            } catch (Exception e) {
                log.error("Ошибка при обработке меню: {}", e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }, AppUtil.ITERATION_LOOP);
    }

    private Optional<UUID> checkValidUUID() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_ID);
            String inputId = SCANNER.nextLine();
            log.debug("Попытка {}: введён UUID '{}'", i + 1, inputId);
            if (inputId.matches(RegexConstant.UUID_REGEX)) {
                return Optional.of(UUID.fromString(inputId));
            } else {
                log.warn("Введён некорректный UUID: {}", inputId);
                if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                    System.out.println(ERROR_ENTER_UUID_MESSAGE);
                } else {
                    log.error("Превышено количество попыток ввода UUID");
                    AppUtil.exitByFromAttempt();
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> checkValidLastNameSearch() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_LAST_NAME_SEARCH);
            String input = SCANNER.nextLine().trim();
            log.debug("Попытка {}: введена фамилия сотрудника для поиска '{}'", i + 1, input);
            if (input.matches(RegexConstant.FIRS_AND_LAST_NAME_REGEX)) {
                return Optional.of(input);
            } else {
                log.warn("Введена некорректная фамилия: {}", input);
                if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                    System.out.println(ERROR_ENTER_LAST_NAME_MESSAGE);
                } else {
                    log.error("Превышено количество попыток ввода фамилии");
                    AppUtil.exitByFromAttempt();
                }
            }
        }
        return Optional.empty();
    }
}