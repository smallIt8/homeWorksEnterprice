package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

	@Override
	public void create(Category category) {

	}

	@Override
	public void createBatch(List<Category> categories) {

	}

	@Override
	public Optional<Category> findById(UUID category) {
		return Optional.empty();
	}

	@Override
	public void update(Category category) {

	}

	@Override
	public List<Category> findAll(UUID currentPersonId) {
		return List.of();
	}

	@Override
	public void delete(UUID categoryId, UUID creatorId) {

	}
}
