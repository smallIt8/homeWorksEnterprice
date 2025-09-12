package org.example.repository;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.model.QCategory.category;
import static org.example.model.QTransaction.transaction;
import static org.example.util.HibernateSessionFactoryUtil.openSession;

@Slf4j
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

	@Override
	public void create(Transaction transaction) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.persist(transaction);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании транзакции '{}': {}",
					  transaction.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Transaction> transactions) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			for (int i = 0; i < transactions.size(); i++) {
				session.persist(transactions.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}

			session.getTransaction().commit();
			log.info("Добавлено транзакций: {}", transactions.size());
		} catch (Exception e) {
			log.error("Ошибка при массовом добавлении транзакций: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Transaction> findById(UUID transactionId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Transaction result = new JPAQuery<Transaction>(session)
					.select(transaction)
					.from(transaction)
					.join(transaction.category, category).fetchJoin()
					.where(transaction.transactionId.eq(transactionId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных транзакции с ID {}: {}",
					  transactionId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Transaction transaction) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPAUpdateClause(session, QTransaction.transaction)
					.where(QTransaction.transaction.transactionId.eq(transaction.getTransactionId()))
					.set(QTransaction.transaction.name, transaction.getName())
					.set(QTransaction.transaction.type, transaction.getType())
					.set(QTransaction.transaction.category, transaction.getCategory())
					.set(QTransaction.transaction.amount, transaction.getAmount())
					.set(QTransaction.transaction.transactionDate, transaction.getTransactionDate())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных транзакции с ID {}: {}",
					  transaction.getTransactionId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Transaction> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {

			return new JPAQuery<Transaction>(session)
					.select(transaction)
					.from(transaction)
					.join(transaction.category, category).fetchJoin()
					.where(transaction.creator.personId.eq(currentPersonId))
					.fetch();

		} catch (Exception e) {
			log.error("Ошибка при получении списка транзакций для пользователя с ID {}: {}",
					  currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID transactionId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPADeleteClause(session, transaction)
					.where(transaction.transactionId.eq(transactionId)
								   .and(transaction.creator.personId.eq(currentPersonId)))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных транзакции с  ID {}: {}",
					  transactionId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}