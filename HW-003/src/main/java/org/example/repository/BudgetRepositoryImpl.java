package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.*;

@Slf4j
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepository {

	@Override
	public void create(Budget budget) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_BUDGET)) {
			statement.setObject(1, budget.getBudgetId());
			statement.setString(2, budget.getName());
			statement.setObject(3, budget.getCategory().getCategoryId());
			statement.setBigDecimal(4, budget.getLimit());
			statement.setDate(5, Date.valueOf(budget.getPeriod().atEndOfMonth()));
			statement.setObject(6, budget.getCreator().getPersonId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при создании бюджета '{}': {}", budget.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Budget> budgets) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_BUDGET)) {
			for (Budget budget : budgets) {
				statement.setObject(1, budget.getBudgetId());
				statement.setString(2, budget.getName());
				statement.setObject(3, budget.getCategory().getCategoryId());
				statement.setBigDecimal(4, budget.getLimit());
				statement.setDate(5, Date.valueOf(budget.getPeriod().atEndOfMonth()));
				statement.setObject(6, budget.getCreator().getPersonId());
				statement.addBatch();
			}
			int[] result = statement.executeBatch();
			log.info("Добавлено бюджетов: {}", result.length);
		} catch (SQLException e) {
			log.error("Ошибка при массовом добавлении бюджетов: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Budget> findById(UUID budgetId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_BUDGET_JOIN)) {
			statement.setObject(1, budgetId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Budget budget = Budget.builder()
							.budgetId(budgetId)
							.name(query.getString("budget_name"))
							.category(Category.builder()
											  .categoryId(query.getObject("category_id", UUID.class))
											  .name(query.getString("category_name"))
											  .build()
							)
							.limit(query.getBigDecimal("budget_limit"))
							.period(YearMonth.from(query.getDate("period").toLocalDate()))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					return Optional.of(budget);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных бюджета с ID {}: {}", budgetId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void update(Budget budget) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_BUDGET)) {
			statement.setString(1, budget.getName());
			statement.setObject(2, budget.getCategory().getCategoryId());
			statement.setBigDecimal(3, budget.getLimit());
			statement.setDate(4, Date.valueOf(budget.getPeriod().atEndOfMonth()));
			statement.setObject(5, budget.getBudgetId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении данных бюджета с ID {}: {}", budget.getBudgetId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Budget> findAll(UUID currentPersonId) {
		List<Budget> budgets = new ArrayList<>();
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_CREATOR_ALL_BUDGET_JOIN)) {
			statement.setObject(1, currentPersonId);
			try (ResultSet query = statement.executeQuery()) {
				while (query.next()) {
					Budget budget = Budget.builder()
							.budgetId(query.getObject("budget_id", UUID.class))
							.name(query.getString("budget_name"))
							.category(Category.builder()
											  .categoryId(query.getObject("category_id", UUID.class))
											  .name(query.getString("category_name"))
											  .build()
							)
							.limit(query.getBigDecimal("budget_limit"))
							.period(YearMonth.from(query.getDate("period").toLocalDate()))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					budgets.add(budget);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении списка бюджетов для пользователя с ID {}: {}", currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return budgets;
	}

	@Override
	public void delete(UUID budgetId, UUID currentPersonId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_BUDGET)) {
			statement.setObject(1, budgetId);
			statement.setObject(2, currentPersonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при удалении данных бюджета с  ID {}: {}", budgetId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}