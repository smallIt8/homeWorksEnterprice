package org.example.repository;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.FinancialGoal;
import org.example.model.QFinancialGoal;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.model.QFinancialGoal.financialGoal;
import static org.example.util.HibernateSessionFactoryUtil.openSession;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FinancialGoalRepositoryImpl implements FinancialGoalRepository {

	@Override
	public void create(FinancialGoal financialGoal) {
		try (Session session = openSession()) {
			session.beginTransaction();
			session.persist(financialGoal);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании долгосрочной финансовой цели '{}': {}",
					  financialGoal.getFinancialGoalName(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<FinancialGoal> financialGoals) {
		try (Session session = openSession()) {
			session.beginTransaction();

			for (int i = 0; i < financialGoals.size(); i++) {
				session.persist(financialGoals.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}

			session.getTransaction().commit();
			log.info("Добавлено долгосрочных финансовых целей: {}", financialGoals.size());
		} catch (Exception e) {
			log.error("Ошибка при массовом добавлении долгосрочных финансовых целей: {}",
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<FinancialGoal> findById(UUID financialGoalId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			var result = new JPAQuery<FinancialGoal>(session)
					.select(financialGoal)
					.from(financialGoal)
					.where(financialGoal.financialGoalId.eq(financialGoalId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных долгосрочной финансовой цели с ID {}: {}",
					  financialGoalId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(FinancialGoal financialGoal) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPAUpdateClause(session, QFinancialGoal.financialGoal)
					.where(QFinancialGoal.financialGoal.financialGoalId.eq(financialGoal.getFinancialGoalId()))
					.set(QFinancialGoal.financialGoal.financialGoalName, financialGoal.getFinancialGoalName())
					.set(QFinancialGoal.financialGoal.targetAmount, financialGoal.getTargetAmount())
					.set(QFinancialGoal.financialGoal.endDate, financialGoal.getEndDate())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных долгосрочной финансовой цели с ID {}: {}",
					  financialGoal.getFinancialGoalId(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<FinancialGoal> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			return new JPAQuery<FinancialGoal>(session)
					.select(financialGoal)
					.from(financialGoal)
					.where(financialGoal.creator.personId.eq(currentPersonId))
					.fetch();

		} catch (Exception e) {
			log.error("Ошибка при получении списка долгосрочных финансовых целей для пользователя с ID {}: {}",
					  currentPersonId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID financialGoalId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPADeleteClause(session, financialGoal)
					.where(financialGoal.financialGoalId.eq(financialGoalId)
								   .and(financialGoal.creator.personId.eq(currentPersonId)))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных транзакции с  ID {}: {}",
					  financialGoalId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
