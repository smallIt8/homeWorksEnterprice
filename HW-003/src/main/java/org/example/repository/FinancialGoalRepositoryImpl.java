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
public class FinancialGoalRepositoryImpl implements FinancialGoalRepository {

	@Override
	public void create(FinancialGoal financialGoal) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_FINANCIAL_GOAL)) {
			statement.setObject(1, financialGoal.getFinancialGoalId());
			statement.setString(2, financialGoal.getName());
			statement.setBigDecimal(3, financialGoal.getTargetAmount());
			statement.setDate(4, Date.valueOf(financialGoal.getEndDate()));
			statement.setObject(5, financialGoal.getCreator().getPersonId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при создании долгосрочной финансовой цели '{}': {}", financialGoal.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<FinancialGoal> financialGoals) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_FINANCIAL_GOAL)) {
			for (FinancialGoal financialGoal : financialGoals) {
				statement.setObject(1, financialGoal.getFinancialGoalId());
				statement.setString(2, financialGoal.getName());
				statement.setBigDecimal(3, financialGoal.getTargetAmount());
				statement.setDate(4, Date.valueOf(financialGoal.getEndDate()));
				statement.setObject(5, financialGoal.getCreator().getPersonId());
				statement.addBatch();
			}
			int[] result = statement.executeBatch();
			log.info("Добавлено долгосрочных финансовых целей: {}", result.length);
		} catch (SQLException e) {
			log.error("Ошибка при массовом добавлении долгосрочных финансовых целей: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<FinancialGoal> findById(UUID financialGoalId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_FINANCIAL_GOAL)) {
			statement.setObject(1, financialGoalId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					FinancialGoal financialGoal = FinancialGoal.builder()
							.financialGoalId(financialGoalId)
							.name(query.getString("financial_goal_name"))
							.targetAmount(query.getBigDecimal("target_amount"))
							.endDate(query.getDate("end_date").toLocalDate())
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					return Optional.of(financialGoal);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных долгосрочной финансовой цели с ID {}: {}", financialGoalId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void update(FinancialGoal financialGoal) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_FINANCIAL_GOAL)) {
			statement.setString(1, financialGoal.getName());
			statement.setBigDecimal(2, financialGoal.getTargetAmount());
			statement.setDate(3, Date.valueOf(financialGoal.getEndDate()));
			statement.setObject(4, financialGoal.getFinancialGoalId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении данных долгосрочной финансовой цели с ID {}: {}", financialGoal.getFinancialGoalId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<FinancialGoal> findAll(UUID currentPersonId) {
		List<FinancialGoal> financialGoals = new ArrayList<>();
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_CREATOR_ALL_FINANCIAL_GOAL)) {
			statement.setObject(1, currentPersonId);
			try (ResultSet query = statement.executeQuery()) {
				while (query.next()) {
					FinancialGoal financialGoal = FinancialGoal.builder()
							.financialGoalId(query.getObject("financial_goal_id", UUID.class))
							.name(query.getString("financial_goal_name"))
							.targetAmount(query.getBigDecimal("target_amount"))
							.endDate(query.getDate("end_date").toLocalDate())
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.createDate(query.getTimestamp("create_date").toLocalDateTime())
							.build();
					financialGoals.add(financialGoal);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении списка долгосрочных финансовых целей для пользователя с ID {}: {}", currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return financialGoals;
	}

	@Override
	public void delete(UUID financialGoalId, UUID currentPersonId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_FINANCIAL_GOAL)) {
			statement.setObject(1, financialGoalId);
			statement.setObject(2, currentPersonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при удалении данных транзакции с  ID {}: {}", financialGoalId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}