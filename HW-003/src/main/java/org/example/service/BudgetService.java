package org.example.service;

import org.example.dto.BudgetDto;
import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.model.Budget;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BudgetService extends ComponentService<BudgetDto, Budget, PersonDto, UUID> {

	List<BudgetDto> getAllSortByDate(LocalDate date, PersonDto currentPersonDto);

	List<BudgetDto> getAllSortByCategory(CategoryDto categoryDto, PersonDto currentPersonDto);

	List<BudgetDto> getAllSortByCategoryAndDate(CategoryDto categoryDto, LocalDate date, PersonDto currentPersonDto);

	List<BudgetDto> getAllSortByCreateDate(LocalDate createDate, PersonDto currentPersonDto);

}