package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.CategoryService;
import org.example.util.AppUtil;

import java.util.Scanner;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.PERSON_CATEGORY;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class CategoryControllerImpl extends BaseController implements CategoryController {
	private final CategoryService categoryService;

	private static final Scanner SCANNER = new Scanner(System.in);

	@Override
	public void menu() {
		categoryMenu();
	}

	@Override
	public void categoryMenu() {
		log.info("Запуск меню категорий");
		System.out.print(PERSON_CATEGORY);
		AppUtil.loopIterationAndExit((count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню категорий: {}", step);
			switch (step) {
				case STEP_ONE -> categoryService.create();
				case STEP_TWO -> categoryService.update(currentPerson.getPersonId());
				case STEP_THREE -> System.out.println("getAll");
				case STEP_NINE -> categoryService.delete(currentPerson.getPersonId());
				case STEP_BACK -> {
					return true;
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню категорий");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню категорий");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.print(ERROR_ENTER_MESSAGE);
					}
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}
}