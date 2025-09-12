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

import static org.example.model.QBudget.budget;
import static org.example.util.HibernateSessionFactoryUtil.openSession;

@Slf4j
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepository {

	@Override
	public void create(Budget budget) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.persist(budget);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании бюджета '{}': {}",
					  budget.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Budget> budgets) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

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
			log.error("Ошибка при массовом добавлении бюджетов: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Budget> findById(UUID budgetId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Budget result = new JPAQuery<Budget>(session)
					.select(budget)
					.from(budget)
					.where(budget.budgetId.eq(budgetId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных бюджета с ID {}: {}",
					  budgetId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Budget budget) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPAUpdateClause(session, QBudget.budget)
					.where(QBudget.budget.budgetId.eq(budget.getBudgetId()))
					.set(QBudget.budget.name, budget.getName())
					.set(QBudget.budget.category, budget.getCategory())
					.set(QBudget.budget.limit, budget.getLimit())
					.set(QBudget.budget.period, budget.getPeriod())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных бюджета с ID {}: {}",
					  budget.getBudgetId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Budget> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			List<Budget> budgets = new JPAQuery<Budget>(session)
					.select(budget)
					.from(budget)
					.where(budget.creator.personId.eq(currentPersonId))
					.fetch();

			session.getTransaction().commit();
			return budgets;
		} catch (Exception e) {
			log.error("Ошибка при получении списка бюджетов для пользователя с ID {}: {}",
					  currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(UUID budgetId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPADeleteClause(session, budget)
					.where(budget.budgetId.eq(budgetId)
								   .and(budget.creator.personId.eq(currentPersonId)))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных бюджета с  ID {}: {}",
					  budgetId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}