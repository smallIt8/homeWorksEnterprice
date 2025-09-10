package org.example.mapper;

import org.example.dto.CategoryDto;
import org.example.model.Category;

import java.util.List;

public class CategoryMapper {
	public static List<CategoryDto> modelToDtoList(List<Category> categories) {
		return categories.stream()
				.map(CategoryMapper::modelToDto)
				.toList();
	}

	public static CategoryDto modelToDto(Category category) {
		return CategoryDto.builder()
				.categoryId(category.getCategoryId())
				.name(category.getName())
				.type(category.getType())
				.creatorDto(PersonMapper.modelToDtoLight(category.getCreator()))
				.build();
	}

	public static CategoryDto modelToDtoLight(Category category) {
		return CategoryDto.builder()
				.categoryId(category.getCategoryId())
				.name(category.getName())
				.build();
	}

	public static List<Category> dtoToModel(List<CategoryDto> categoriesDto) {
		return categoriesDto.stream()
				.map(CategoryMapper::dtoToModel)
				.toList();
	}

	public static Category dtoToModel(CategoryDto categoryDto) {
		return Category.builder()
				.categoryId(categoryDto.getCategoryId())
				.name(categoryDto.getName())
				.type(categoryDto.getType())
				.creator(PersonMapper.dtoToModelLight(categoryDto.getCreatorDto()))
				.build();
	}

	public static Category dtoToModelLight(CategoryDto categoryDto) {
		return Category.builder()
				.categoryId(categoryDto.getCategoryId())
				.build();
	}
}