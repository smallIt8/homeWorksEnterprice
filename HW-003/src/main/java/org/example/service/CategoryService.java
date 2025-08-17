package org.example.service;

import org.example.dto.BudgetDto;
import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.model.Category;
import org.example.model.Person;

import java.util.UUID;

public interface CategoryService extends Service<Category, PersonDto, UUID> {

	void delete(PersonDto currentPersonDto, CategoryDto categoryDto);

}