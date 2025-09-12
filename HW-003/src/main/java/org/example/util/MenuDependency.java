package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.repository.*;
import org.example.service.*;

@UtilityClass
public class MenuDependency {

	public static FamilyRepository familyRepository() {
		return new FamilyRepositoryImpl();
	}

	public static PersonRepository personRepository() {
		return new PersonRepositoryImpl();
	}

	public static TransactionRepository transactionRepository() {
		return new TransactionRepositoryImpl();
	}

	public static CategoryRepository categoryRepository() {
		return new CategoryRepositoryImpl();
	}

	public static BudgetRepository budgetRepository() {
		return new BudgetRepositoryImpl();
	}

	public static FinancialGoalRepository financialGoalRepository() {
		return new FinancialGoalRepositoryImpl();
	}

	public static PersonService personService() {
		return new PersonServiceImpl(personRepository());
	}

	public static FamilyService familyService() {
		return new FamilyServiceImpl(familyRepository());
	}

	public static TransactionService transactionService() {
		return new TransactionServiceImpl(transactionRepository());
	}

	public static CategoryService categoryService() {
		return new CategoryServiceImpl(categoryRepository());
	}

	public static BudgetService budgetService() {
		return new BudgetServiceImpl(budgetRepository());
	}

	public static FinancialGoalService financialGoalService() {
		return new FinancialGoalServiceImpl(financialGoalRepository());
	}
}

//		public final FamilyRepository FAMILY_REPOSITORY = new FamilyRepositoryImpl();
//		public final PersonRepository PERSON_REPOSITORY = new PersonRepositoryImpl();
//		public final TransactionRepository TRANSACTION_REPOSITORY = new TransactionRepositoryImpl();
//		public final CategoryRepository CATEGORY_REPOSITORY = new CategoryRepositoryImpl();
//		public final BudgetRepository BUDGET_REPOSITORY = new BudgetRepositoryImpl();
//
//		public final FinancialGoalRepository FINANCIAL_GOAL_REPOSITORY = new FinancialGoalRepositoryImpl();
//
//		public final PersonService PERSON_SERVICE = new PersonServiceImpl(PERSON_REPOSITORY);
//		public final FamilyService FAMILY_SERVICE = new FamilyServiceImpl(FAMILY_REPOSITORY);
//		public final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl(TRANSACTION_REPOSITORY);
//		public final CategoryService CATEGORY_SERVICE = new CategoryServiceImpl(CATEGORY_REPOSITORY);
//		public final BudgetService BUDGET_SERVICE = new BudgetServiceImpl(BUDGET_REPOSITORY);
//		public final FinancialGoalService FINANCIAL_GOAL_SERVICE = new FinancialGoalServiceImpl(FINANCIAL_GOAL_REPOSITORY);