package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.Category;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class CategoryRepositoryImpl implements CategoryRepository {

	@Override
	public void create(Category value) {

	}

	@Override
	public Optional<Category> getById(UUID value) {
		return Optional.empty();
	}

	@Override
	public void update(Category value) {

	}

	@Override
	public void delete(UUID value) {

	}
}
