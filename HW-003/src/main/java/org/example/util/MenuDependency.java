package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.mapper.*;
import org.example.repository.*;
import org.example.service.*;
import org.example.validator.BudgetValidator;

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

	public static PersonMapper personMapper() {
		return new PersonMapper();
	}

	public static CategoryMapper categoryMapper() {
		return new CategoryMapper(personMapper());
	}

	public static FamilyMapper familyMapper() {
		return new FamilyMapper(personMapper());
	}

	public static TransactionMapper transactionMapper() {
		return new TransactionMapper(categoryMapper(), personMapper());
	}

	public static BudgetMapper budgetMapper() {
		return new BudgetMapper(categoryMapper(), personMapper());
	}

	public static FinancialGoalMapper financialGoalMapper() {
		return new FinancialGoalMapper(personMapper());
	}

	public static PersonService personService() {
		return new PersonServiceImpl(personRepository(), personMapper());
	}

	public static FamilyService familyService() {
		return new FamilyServiceImpl(familyRepository(), familyMapper(), personMapper());
	}

	public static TransactionService transactionService() {
		return new TransactionServiceImpl(transactionRepository(), transactionMapper(), personMapper());
	}

	public static CategoryService categoryService() {
		return new CategoryServiceImpl(categoryRepository(), categoryMapper(), personMapper());
	}

	public static BudgetService budgetService() {
		return new BudgetServiceImpl(budgetRepository(), budgetMapper(), personMapper(), new BudgetValidator());
	}

	public static FinancialGoalService financialGoalService() {
		return new FinancialGoalServiceImpl(financialGoalRepository(), financialGoalMapper(), personMapper());
	}
}
