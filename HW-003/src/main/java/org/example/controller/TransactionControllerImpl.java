package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Transaction;
import org.example.model.TransactionType;
import org.example.service.TransactionService;
import org.example.util.AppUtil;

import java.util.List;
import java.util.Scanner;

import static org.example.util.constant.ErrorMessageConstant.ERROR_ENTER_MESSAGE;
import static org.example.util.constant.MenuConstant.PERSON_TRANSACTION_LIST_MENU;
import static org.example.util.constant.MenuConstant.PERSON_TRANSACTION_MENU;
import static org.example.util.constant.StepConstant.*;

@Slf4j
@RequiredArgsConstructor

public class TransactionControllerImpl extends BaseController implements TransactionController {
	private final TransactionService transactionService;

	private static final Scanner SCANNER = new Scanner(System.in);

	@Override
	public void menu() {
		transactionMenu();
	}

	@Override
	public void transactionMenu() {
//		log.info("Запуск меню транзакций");
//		System.out.print(PERSON_TRANSACTION_MENU);
//		AppUtil.loopIterationAndExit((count) -> {
//			String step = SCANNER.nextLine();
//			log.debug("Пользователь ввёл шаг меню транзакций: {}", step);
//			switch (step) {
//				case STEP_ONE -> transactionService.create();
//				case STEP_TWO -> transactionService.update(currentPerson);
//				case STEP_THREE -> transactionListMenu();
//				case STEP_NINE -> transactionService.delete(currentPerson.getPersonId());
//				case STEP_BACK -> {
//					return true;
//				}
//				case STEP_ZERO -> {
//					log.info("Выход из приложения меню транзакций");
//					AppUtil.exit();
//				}
//				default -> {
//					log.warn("Ошибка при обработке меню транзакций");
//					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
//						System.out.print(ERROR_ENTER_MESSAGE);
//					}
//				}
//			}
//			return false;
//		}, AppUtil.MAX_ITERATION_LOOP);
	}

	@Override
	public void transactionListMenu() {
		log.info("Запуск меню списка транзакций");
		System.out.print(PERSON_TRANSACTION_LIST_MENU);
		AppUtil.loopIterationAndExit((Integer count) -> {
			String step = SCANNER.nextLine();
			log.debug("Пользователь ввёл шаг меню списка транзакций: {}", step);
			switch (step) {
				case STEP_ONE -> transactionService.getAll();
				case STEP_TWO -> {
					TransactionType type = TransactionType.INCOME;
					List<Transaction> incomeTransactions = transactionService.getAllSortByType(type, currentPerson.getPersonId());
					incomeTransactions.forEach(System.out::println);

				}
				case STEP_THREE -> {
					TransactionType type = TransactionType.INCOME;
					List<Transaction> incomeByDate = transactionService.getAllSortByTypeAndDate(type, currentPerson.getPersonId());
					incomeByDate.forEach(System.out::println);
				}
				case STEP_FOUR -> {
					TransactionType type = TransactionType.INCOME;
					List<Transaction> incomeByCategory = transactionService.getAllSortByTypeAndCategory(type, currentPerson.getPersonId());
					incomeByCategory.forEach(System.out::println);
				}
				case STEP_FIVE -> {
					TransactionType type = TransactionType.EXPENSE;
					List<Transaction> expenseTransactions = transactionService.getAllSortByType(type, currentPerson.getPersonId());
					expenseTransactions.forEach(System.out::println);
				}
				case STEP_SIX -> {
					TransactionType type = TransactionType.EXPENSE;
					List<Transaction> expenseByDate = transactionService.getAllSortByTypeAndDate(type, currentPerson.getPersonId());
					expenseByDate.forEach(System.out::println);
				}
				case STEP_SEVEN -> {
					TransactionType type = TransactionType.EXPENSE;
					List<Transaction> expenseByCategory = transactionService.getAllSortByTypeAndCategory(type, currentPerson.getPersonId());
					expenseByCategory.forEach(System.out::println);
				}
				case STEP_BACK -> {
					return true;
				}
				case STEP_ZERO -> {
					log.info("Выход из приложения меню списка транзакций");
					AppUtil.exit();
				}
				default -> {
					log.warn("Ошибка при обработке меню списка транзакций");
					if (count < AppUtil.MAX_ITERATION_LOOP_TO_MESSAGE) {
						System.out.println(ERROR_ENTER_MESSAGE);
					}
				}
			}
			return false;
		}, AppUtil.MAX_ITERATION_LOOP);
	}
}