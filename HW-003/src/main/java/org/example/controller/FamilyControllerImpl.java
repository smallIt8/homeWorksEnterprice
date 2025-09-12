package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.service.FamilyService;
import org.example.util.AppUtil;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.PERSON_FAMILY_MENU;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class FamilyControllerImpl extends BaseController implements FamilyController {
	private final FamilyService familyService;

	private static final Scanner SCANNER = new Scanner(System.in);

	@Override
	public void menu() {
		familyMenu();
	}

	@Override
	public void familyMenu() {
//		log.info("Запуск меню семейной группы");
//		System.out.print(PERSON_FAMILY_MENU);
//		AppUtil.loopIterationAndExit((count) -> {
//			String step = SCANNER.nextLine();
//			log.debug("Пользователь ввёл шаг меню семейной группы: {}", step);
//			switch (step) {
//				case STEP_ONE -> familyService.create(currentPerson);
//				case STEP_TWO -> {
//					String familyNameInput = SCANNER.nextLine();
//					Family familyName = new Family(UUID.randomUUID(), familyNameInput, currentPerson);
//					Optional<Family> joinedFamily = familyService.joinFamily(currentPerson, familyName);
//					joinedFamily.ifPresent(family -> {
//						// Заглушка
//					});
//				}
//				case STEP_THREE -> {
//					String email = SCANNER.nextLine();
//					boolean result = familyService.addMember(email, currentPerson.getPersonId());
//				}
//				case STEP_FOUR -> familyService.update(currentPerson);
//				case STEP_FIVE -> {
//					boolean result = familyService.exitFamily(currentPerson);
//				}
//				case STEP_NINE -> familyService.delete(currentPerson.getPersonId());
//				case STEP_BACK -> {
//					return true;
//				}
//				case STEP_ZERO -> {
//					log.info("Выход из приложения меню семейной группы");
//					AppUtil.exit();
//				}
//				default -> {
//					log.warn("Ошибка при обработке меню семейной группы");
//					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
//						System.out.print(ERROR_ENTER_MESSAGE);
//					}
//				}
//			}
//			return false;
//		}, AppUtil.MAX_ITERATION_LOOP);
	}
}