package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDto;
import org.example.model.Category;

import java.util.List;

@RequiredArgsConstructor
public class CategoryMapper {

	private final PersonMapper personMapper;

	public List<CategoryDto> mapModelToDtoList(List<Category> categories) {
		return categories.stream()
				.map(this::mapModelToDto)
				.toList();
	}

	public CategoryDto mapModelToDto(Category category) {
		return CategoryDto.builder()
				.categoryId(category.getCategoryId())
				.categoryName(category.getCategoryName())
				.type(category.getType())
				.creatorDto(personMapper.mapModelToDtoLight(category.getCreator()))
				.build();
	}

	public CategoryDto mapModelToDtoLight(Category category) {
		return CategoryDto.builder()
				.categoryId(category.getCategoryId())
				.categoryName(category.getCategoryName())
				.build();
	}

	public List<Category> mapDtoToModelList(List<CategoryDto> categoriesDto) {
		return categoriesDto.stream()
				.map(this::mapDtoToModel)
				.toList();
	}

	public Category mapDtoToModel(CategoryDto categoryDto) {
		return Category.builder()
				.categoryId(categoryDto.getCategoryId())
				.categoryName(categoryDto.getCategoryName())
				.type(categoryDto.getType())
				.creator(personMapper.mapDtoToModelLight(categoryDto.getCreatorDto()))
				.build();
	}

	public Category mapDtoToModelLight(CategoryDto categoryDto) {
		return Category.builder()
				.categoryId(categoryDto.getCategoryId())
				.build();
	}
}
