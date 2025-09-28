package org.example.repository;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Budget;
import org.example.model.QBudget;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.model.QBudget.budget;
import static org.example.model.QCategory.category;
import static org.example.util.HibernateSessionFactoryUtil.openSession;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BudgetRepositoryImpl implements BudgetRepository {

	@Override
	public void create(Budget budget) {
		try (Session session = openSession()) {
			session.beginTransaction();
			session.persist(budget);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании бюджета '{}': {}",
					  budget.getBudgetName(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Budget> budgets) {
		try (Session session = openSession()) {
			session.beginTransaction();

			for (int i = 0; i < budgets.size(); i++) {
				session.persist(budgets.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}

			session.getTransaction().commit();
			log.info("Добавлено бюджетов: {}", budgets.size());
		} catch (Exception e) {
			log.error("Ошибка при массовом добавлении бюджетов: {}",
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Budget> findById(UUID budgetId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			var result = new JPAQuery<Budget>(session)
					.select(budget)
					.from(budget)
					.join(budget.category, category).fetchJoin()
					.where(budget.budgetId.eq(budgetId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных бюджета с ID {}: {}",
					  budgetId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Budget budget) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPAUpdateClause(session, QBudget.budget)
					.where(QBudget.budget.budgetId.eq(budget.getBudgetId()))
					.set(QBudget.budget.budgetName, budget.getBudgetName())
					.set(QBudget.budget.category, budget.getCategory())
					.set(QBudget.budget.limit, budget.getLimit())
					.set(QBudget.budget.period, budget.getPeriod())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных бюджета с ID {}: {}",
					  budget.getBudgetId(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Budget> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {

			return new JPAQuery<Budget>(session)
					.select(budget)
					.from(budget)
					.join(budget.category, category).fetchJoin()
					.where(budget.creator.personId.eq(currentPersonId))
					.fetch();

		} catch (Exception e) {
			log.error("Ошибка при получении списка бюджетов для пользователя с ID {}: {}",
					  currentPersonId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(UUID budgetId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPADeleteClause(session, budget)
					.where(budget.budgetId.eq(budgetId)
								   .and(budget.creator.personId.eq(currentPersonId)))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных бюджета с  ID {}: {}",
					  budgetId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
