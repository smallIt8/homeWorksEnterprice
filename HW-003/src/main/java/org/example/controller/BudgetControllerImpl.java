package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.BudgetService;
import org.example.util.AppUtil;

import java.util.Scanner;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.PERSON_BUDGET;
import static org.example.util.constant.MenuConstant.PERSON_TRANSACTION_LIST;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class BudgetControllerImpl extends BaseController implements BudgetController {
	private final BudgetService budgetService;

	private static final Scanner SCANNER = new Scanner(System.in);

	@Override
	public void menu() {
		budgetMenu();
	}

	@Override
	public void budgetMenu() {
		log.info("Запуск меню бюджет");
		System.out.print(PERSON_BUDGET);
		AppUtil.loopIterationAndExit((count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню бюджет: {}", step);
			switch (step) {
				case STEP_ONE -> budgetService.create();
				case STEP_TWO -> budgetService.update(currentPerson.getPersonId());
				case STEP_THREE -> budgetListMenu();
				case STEP_NINE -> budgetService.delete(currentPerson.getPersonId());
				case STEP_BACK -> {
					return true;
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню бюджет");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню бюджет");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.print(ERROR_ENTER_MESSAGE);
					}
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}

	@Override
	public void budgetListMenu() {
		log.info("Запуск меню списка бюджета");
		System.out.print(PERSON_TRANSACTION_LIST);
		AppUtil.loopIterationAndExit((Integer count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню списка бюджета: {}", step);
			switch (step) {
				case STEP_ONE -> System.out.println("getAll");
				case STEP_TWO -> System.out.println("sortBudgetPeriod");
				case STEP_THREE -> System.out.println("sortCategory");
				case STEP_FOUR -> System.out.println("sortCategoryPeriod");
				case STEP_BACK -> {
					return true;
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню списка бюджета");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню списка бюджета");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.println(ERROR_ENTER_MESSAGE);
					}
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}
}