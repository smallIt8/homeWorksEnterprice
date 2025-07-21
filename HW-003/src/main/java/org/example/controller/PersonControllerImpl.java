package org.example.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.PersonService;
import org.example.util.AppUtil;
import org.example.util.MenuDependency;

import java.util.Scanner;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.PERSON_MENU;
import static org.example.util.constant.MenuConstant.PERSON_UPDATE_MENU;
import static org.example.util.constant.StepConstant.*;

@Getter
@Slf4j
@RequiredArgsConstructor

public class PersonControllerImpl extends BaseController implements PersonController {
	private final PersonService personService;
	private final FamilyControllerImpl familyControllerImpl;
	private final TransactionControllerImpl transactionControllerImpl;
	private final CategoryControllerImpl categoryControllerImpl;
	private final BudgetControllerImpl budgetControllerImpl;
	private final FinancialGoalControllerImpl financialGoalControllerImpl;

	private static final Scanner SCANNER = new Scanner(System.in);

	@Override
	public void menu() {
		personMenu();
	}

	@Override
	public void personMenu() {
		log.info("Запуск меню пользователя");
		System.out.print(PERSON_MENU);
		AppUtil.loopIterationAndExit((count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню пользователя: {}", step);
			switch (step) {
				case STEP_ONE -> {
					transactionControllerImpl.menu();
					System.out.print(PERSON_MENU);
				}
				case STEP_TWO -> {
					categoryControllerImpl.menu();
					System.out.print(PERSON_MENU);
				}
				case STEP_THREE -> {
					budgetControllerImpl.menu();
					System.out.print(PERSON_MENU);
				}
				case STEP_FOUR -> {
					financialGoalControllerImpl.menu();
					System.out.print(PERSON_MENU);
				}
				case STEP_FIVE -> {
					familyControllerImpl.menu();
					System.out.print(PERSON_MENU);
				}
				case STEP_SIX -> personUpdateMenu();
				case STEP_NINE -> {
					personService.delete(currentPerson.getPersonId());
					currentPerson = null;
					MenuDependency.start();
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню пользователя");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню пользователя");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.print(ERROR_ENTER_MESSAGE);
					}
					return false;
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	public void personUpdateMenu() {
		log.info("Запуск меню пользователя/обновление");
		System.out.print(PERSON_UPDATE_MENU);
		AppUtil.loopIterationAndExit((count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню пользователя/обновление: {}", step);
			switch (step) {
				case STEP_ONE -> {
					personService.update(currentPerson);
					System.out.print(PERSON_UPDATE_MENU);
				}
				case STEP_TWO -> {
					personService.updatePassword(currentPerson);
					System.out.print(PERSON_UPDATE_MENU);
				}
				case STEP_BACK -> {
					return true;
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню пользователя/обновление");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню пользователя/обновление");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.print(ERROR_ENTER_MESSAGE);
					}
					return false;
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}
}