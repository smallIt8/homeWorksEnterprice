package org.example.service;

import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.model.Category;

import java.util.UUID;

public interface CategoryService extends ComponentService<CategoryDto, Category, PersonDto, UUID> {
}
