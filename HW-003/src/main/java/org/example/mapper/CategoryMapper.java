package org.example.mapper;

import org.example.dto.CategoryDto;
import org.example.dto.TransactionDto;
import org.example.model.Category;
import org.example.model.Transaction;

import java.util.List;

public class CategoryMapper {
	public static CategoryDto modelToDto(Category category) {
		return CategoryDto.builder()
				.categoryId(category.getCategoryId())
				.name(category.getName())
				.type(category.getType())
				.creator(category.getCreator())
				.build();
	}

	public static List<CategoryDto> modelToDtoList(List<Category> categories) {
		return categories.stream()
				.map(CategoryMapper::modelToDto)
				.toList();
	}

	public static Category dtoToModel(CategoryDto categoryDto) {
		return Category.builder()
				.categoryId(categoryDto.getCategoryId())
				.name(categoryDto.getName())
				.type(categoryDto.getType())
				.creator(categoryDto.getCreator())
				.build();
	}
}