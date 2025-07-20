package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FinancialGoalService;
import org.example.util.AppUtil;

import java.util.Scanner;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.PERSON_GOAL;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class FinancialGoalControllerImpl extends BaseController implements FinancialGoalController {
	private final FinancialGoalService financialGoalService;

	private static final Scanner SCANNER = new Scanner(System.in);

	@Override
	public void menu() {
		financialGoalMenu();
	}

	@Override
	public void financialGoalMenu() {
		log.info("Запуск меню финансовых целей");
		System.out.print(PERSON_GOAL);
		AppUtil.loopIterationAndExit((count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню финансовых целей: {}", step);
			switch (step) {
				case STEP_ONE -> financialGoalService.create();
				case STEP_TWO -> financialGoalService.update(currentPerson.getPersonId());
				case STEP_THREE -> financialGoalService.getAll();
				case STEP_NINE -> financialGoalService.delete(currentPerson.getPersonId());
				case STEP_BACK -> {
					return true;
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню финансовых целей");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню финансовых целей");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.print(ERROR_ENTER_MESSAGE);
					}
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}
}