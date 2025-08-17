package org.example.mapper;

import org.example.dto.CategoryDto;
import org.example.model.Category;

public class CategoryMapper {
	public static CategoryDto modelToDto(Category category) {
		return new CategoryDto(
				category.getCategoryId(),
				category.getCategoryName(),
				category.getType(),
				category.getPerson());
	}

	public static Category dtoToModel(CategoryDto categoryDto) {
		return new Category(
				categoryDto.getCategoryId(),
				categoryDto.getCategoryName(),
				categoryDto.getType(),
				categoryDto.getPerson());
	}
}