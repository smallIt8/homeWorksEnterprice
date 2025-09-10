package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.*;

@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

	@Override
	public void create(Category category) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_CATEGORY)) {
			statement.setObject(1, category.getCategoryId());
			statement.setString(2, category.getName());
			statement.setString(3, category.getType().name());
			statement.setObject(4, category.getCreator().getPersonId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при создании транзакции '{}': {}", category.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Category> categories) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_CATEGORY)) {
			for (Category category : categories) {
				statement.setObject(1, category.getCategoryId());
				statement.setString(2, category.getName());
				statement.setString(3, category.getType().name());
				statement.setObject(4, category.getCreator().getPersonId());
				statement.addBatch();
			}
			int[] result = statement.executeBatch();
			log.info("Добавлено категорий: {}", result.length);
		} catch (SQLException e) {
			log.error("Ошибка при массовом добавлении категорий: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Category> findById(UUID categoryId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_CATEGORY)) {
			statement.setObject(1, categoryId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Category category = Category.builder()
							.categoryId(categoryId)
							.name(query.getString("category_name"))
							.type(CategoryType.valueOf(query.getString("type")))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					return Optional.of(category);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных категории с ID {}: {}", categoryId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void update(Category category) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_CATEGORY)) {
			statement.setString(1, category.getName());
			statement.setObject(2, category.getCategoryId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении данных категории с ID {}: {}", category.getCategoryId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Category> findAll(UUID currentPersonId) {
		List<Category> categories = new ArrayList<>();
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_CREATOR_ALL_CATEGORY)) {
			statement.setObject(1, currentPersonId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Category category = Category.builder()
							.categoryId(resultSet.getObject("category_id", UUID.class))
							.name(resultSet.getString("category_name"))
							.type(CategoryType.valueOf(resultSet.getString("type")))
							.creator(Person.builder()
											 .personId(resultSet.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					categories.add(category);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении списка категорий для пользователя '{}': {}", currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return categories;
	}

	@Override
	public void delete(UUID categoryId, UUID currentPersonId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_CATEGORY)) {
			statement.setObject(1, categoryId);
			statement.setObject(2, currentPersonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при удалении данных категории с  ID {}: {}", categoryId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}