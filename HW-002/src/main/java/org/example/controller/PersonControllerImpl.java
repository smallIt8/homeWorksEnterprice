package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Person;
import org.example.service.PersonService;
import org.example.util.AppUtil;
import org.example.util.constant.RegexConstant;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.example.util.constant.ExceptionMessage.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.ExceptionMessage.ERROR_ENTER_UUID_MESSAGE;
import static org.example.util.constant.MenuPersonConstant.ENTER_ID;
import static org.example.util.constant.MenuPersonConstant.MAIN_MENU;
import static org.example.util.constant.StepConstant.*;

@Slf4j

public class PersonControllerImpl {
    private final PersonService personService;
    private static final Scanner SCANNER = new Scanner(System.in);

    public void WelcomePerson() {
        System.out.print(MAIN_MENU);
        AppUtil.loopIterationAndExit((int count) -> {
            try {
                String step = SCANNER.nextLine();
                switch (step) {
                    case STEP_ONE -> personService.create();
                    case STEP_TWO -> {
                        // A preparation for the possibility of sorting the list in the future
                        List<Person> persons = personService.getAll();
                    }
                    case STEP_THREE -> checkValidUUID().ifPresent(personId -> {
                        // Preparation for the possibility of updating and deleting a person in the future
                        Optional<Person> person = personService
                                .getById(personId);
                    });
                    case STEP_FOUR -> checkValidUUID().ifPresent(personId -> {
                        // Preparation for the possibility of updating and deleting a person in the future
                        Optional<Person> person = personService
                                .updateById(personId);
                    });
                    case STEP_FIVE -> checkValidUUID().ifPresent(personService::delete);
                    case STEP_ZERO -> AppUtil.exit();
                    default -> {
                        if (count < AppUtil.ITERATION_LOOP_TO_MESSAGE)
                            System.out.print(ERROR_ENTER_MESSAGE);
                    }
                }
                System.out.print(MAIN_MENU);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }, AppUtil.ITERATION_LOOP);
    }

    private Optional<UUID> checkValidUUID() {
        for (int i = 0; i < AppUtil.ITERATION_LOOP; i++) {
            System.out.print(ENTER_ID);
            String inputId = SCANNER.nextLine();
            if (inputId.matches(RegexConstant.UUID_REGEX)) {
                return Optional.of(UUID.fromString(inputId));
            } else {
                if (i < AppUtil.ITERATION_LOOP_TO_MESSAGE) {
                    System.out.println(ERROR_ENTER_UUID_MESSAGE);
                } else {
                    AppUtil.exitByFromAttempt();
                }
            }
        }
        return Optional.empty();
    }

    public PersonControllerImpl(PersonService personService) {
        this.personService = personService;
    }
}