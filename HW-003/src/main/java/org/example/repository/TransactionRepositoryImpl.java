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
public class TransactionRepositoryImpl implements TransactionRepository {

	@Override
	public void create(Transaction transaction) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_TRANSACTION)) {
			statement.setObject(1, transaction.getTransactionId());
			statement.setString(2, transaction.getName());
			statement.setString(3, transaction.getType().name());
			statement.setObject(4, transaction.getCategory().getCategoryId());
			statement.setBigDecimal(5, transaction.getAmount());
			statement.setObject(6, transaction.getCreator().getPersonId());
			statement.setDate(7, Date.valueOf(transaction.getTransactionDate()));
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при создании транзакции '{}': {}", transaction.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Transaction> transactions) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_TRANSACTION)) {
			for (Transaction transaction : transactions) {
				statement.setObject(1, transaction.getTransactionId());
				statement.setString(2, transaction.getName());
				statement.setString(3, transaction.getType().name());
				statement.setObject(4, transaction.getCategory().getCategoryId());
				statement.setBigDecimal(5, transaction.getAmount());
				statement.setObject(6, transaction.getCreator().getPersonId());
				statement.setDate(7, Date.valueOf(transaction.getTransactionDate()));
				statement.addBatch();
			}
			int[] result = statement.executeBatch();
			log.info("Добавлено транзакций: {}", result.length);
		} catch (SQLException e) {
			log.error("Ошибка при массовом добавлении транзакций: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Transaction> findById(UUID transactionId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_TRANSACTION_JOIN)) {
			statement.setObject(1, transactionId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Transaction transaction = Transaction.builder()
							.transactionId(transactionId)
							.name(query.getString("transaction_name"))
							.type(TransactionType.valueOf(query.getString("type")))
							.category(Category.builder()
											  .categoryId(query.getObject("category_id", UUID.class))
											  .name(query.getString("category_name"))
											  .build()
							)
							.amount(query.getBigDecimal("amount"))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.transactionDate(query.getDate("transaction_date").toLocalDate())
							.build();
					return Optional.of(transaction);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных транзакции с ID {}: {}", transactionId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void update(Transaction transaction) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_TRANSACTION)) {
			statement.setString(1, transaction.getName());
			statement.setString(2, transaction.getType().name());
			statement.setObject(3, transaction.getCategory().getCategoryId());
			statement.setBigDecimal(4, transaction.getAmount());
			statement.setDate(5, Date.valueOf(transaction.getTransactionDate()));
			statement.setObject(6, transaction.getTransactionId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении данных транзакции с ID {}: {}", transaction.getTransactionId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Transaction> findAll(UUID currentPersonId) {
		List<Transaction> transactions = new ArrayList<>();
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_CREATOR_ALL_TRANSACTION_JOIN)) {
			statement.setObject(1, currentPersonId);
			try (ResultSet query = statement.executeQuery()) {
				while (query.next()) {
					Transaction transaction = Transaction.builder()
							.transactionId(query.getObject("transaction_id", UUID.class))
							.name(query.getString("transaction_name"))
							.type(TransactionType.valueOf(query.getString("type")))
							.category(Category.builder()
											  .categoryId(query.getObject("category_id", UUID.class))
											  .name(query.getString("category_name"))
											  .build()
							)
							.amount(query.getBigDecimal("amount"))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.transactionDate(query.getDate("transaction_date").toLocalDate())
							.createDate(query.getTimestamp("create_date").toLocalDateTime())
							.build();
					transactions.add(transaction);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении списка транзакций для пользователя с ID {}: {}", currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return transactions;
	}

	@Override
	public void delete(UUID transactionId, UUID currentPersonId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_TRANSACTION)) {
			statement.setObject(1, transactionId);
			statement.setObject(2, currentPersonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при удалении данных транзакции с  ID {}: {}", transactionId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}