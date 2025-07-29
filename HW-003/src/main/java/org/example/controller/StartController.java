package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Person;
import org.example.service.StartService;
import org.example.util.AppUtil;

import java.util.Optional;
import java.util.Scanner;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.MAIN_MENU;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class StartController {
	private final StartService startService;
	private final PersonControllerImpl personControllerImpl;

	private static final Scanner SCANNER = new Scanner(System.in);

	public void startMenu() {
		log.info("Запуск главного меню приложения");
		System.out.print(MAIN_MENU);
		AppUtil.loopIterationAndExit((count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг главного меню: {}", step);
			switch (step) {
				case STEP_ONE -> {
					Optional<Person> person = startService.entry();
					person.ifPresent(p -> {
						forwardCurrentPerson(p);
						personControllerImpl.menu();
					});
				}
				case STEP_TWO -> {
					startService.create();
					System.out.print(MAIN_MENU);
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке главного меню");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE)
						System.out.print(ERROR_ENTER_MESSAGE);
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	private void forwardCurrentPerson(Person person) {
		personControllerImpl.setCurrentPerson(person);
		personControllerImpl.getFamilyControllerImpl().setCurrentPerson(person);
		personControllerImpl.getTransactionControllerImpl().setCurrentPerson(person);
		personControllerImpl.getCategoryControllerImpl().setCurrentPerson(person);
		personControllerImpl.getBudgetControllerImpl().setCurrentPerson(person);
		personControllerImpl.getFinancialGoalControllerImpl().setCurrentPerson(person);
	}
}