package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.controller.*;
import org.example.repository.*;
import org.example.service.*;

@UtilityClass

public class MenuDependency {

	public static void start() {
		FamilyRepository familyRepository = new FamilyRepositoryImpl();
		PersonRepository personRepository = new PersonRepositoryImpl(familyRepository);
		TransactionRepository transactionRepository = new TransactionRepositoryImpl();
		CategoryRepository categoryRepository = new CategoryRepositoryImpl();
		BudgetRepository budgetRepository = new BudgetRepositoryImpl();
		FinancialGoalRepository financialGoalRepository = new FinancialGoalRepositoryImpl();

		PersonService personService = new PersonServiceImpl(personRepository);
		FamilyService familyService = new FamilyServiceImpl(familyRepository);
		TransactionService transactionService = new TransactionServiceImpl(transactionRepository);
		CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
		BudgetService budgetService = new BudgetServiceImpl(budgetRepository);
		FinancialGoalService financialGoalService = new FinancialGoalServiceImpl(financialGoalRepository);
		StartService startService = new StartServiceImpl(personService);

		FamilyControllerImpl familyController = new FamilyControllerImpl(familyService);
		TransactionControllerImpl transactionController = new TransactionControllerImpl(transactionService);
		CategoryControllerImpl categoryController = new CategoryControllerImpl(categoryService);
		BudgetControllerImpl budgetController = new BudgetControllerImpl(budgetService);
		FinancialGoalControllerImpl financialGoalController = new FinancialGoalControllerImpl(financialGoalService);
		PersonControllerImpl personController = new PersonControllerImpl(personService, familyController, transactionController, categoryController, budgetController, financialGoalController);
		StartController startController = new StartController(startService, personController);
		startController.startMenu();

	}
}