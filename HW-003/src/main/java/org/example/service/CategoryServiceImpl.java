package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Category;
import org.example.model.Person;
import org.example.repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	public void create() {

	}

	@Override
	public Optional<Category> update(Person entity) {
		return Optional.empty();
	}

	@Override
	public void delete(UUID value) {

	}
}