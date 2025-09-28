package org.example.repository;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.model.QFamily;
import org.example.model.QPerson;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.model.QFamily.family;
import static org.example.util.HibernateSessionFactoryUtil.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FamilyRepositoryImpl implements FamilyRepository {

	@Override
	public void create(Family family) {
		try (Session session = openSession()) {
			session.beginTransaction();
			session.persist(family);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании семейной группы '{}': {}",
					  family.getFamilyName(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Family> families) {
		try (Session session = openSession()) {
			session.beginTransaction();

			for (int i = 0; i < families.size(); i++) {
				session.persist(families.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}

			session.getTransaction().commit();
			log.info("Добавлено семейных групп: {}", families.size());
		} catch (Exception e) {
			log.error("Ошибка при массовом добавлении семейных групп: {}",
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Family> findById(UUID familyId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			var result = new JPAQuery<Family>(session)
					.select(family)
					.from(family)
					.where(family.familyId.eq(familyId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных семейной группы с ID {}: {}",
					  familyId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Family family) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPAUpdateClause(session, QFamily.family)
					.where(QFamily.family.familyId.eq(family.getFamilyId()))
					.set(QFamily.family.familyName, family.getFamilyName())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных семейной группы с ID {}: {}",
					  family.getFamilyId(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Family> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			return new JPAQuery<Family>(session)
					.select(QFamily.family)
					.distinct()
					.from(QFamily.family)
					.leftJoin(QFamily.family.members, QPerson.person).fetchJoin()
					.where(QFamily.family.creator.personId.eq(currentPersonId)
								   .or(QPerson.person.personId.eq(currentPersonId)))
					.orderBy(QFamily.family.familyName.asc())
					.fetch();

		} catch (Exception e) {
			log.error("Ошибка при получении списка всех семейных групп пользователя с ID {}: {}",
					  currentPersonId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Family> findAllByOwner(UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			return new JPAQuery<Family>(session)
					.select(family)
					.from(family)
					.where(family.creator.personId.eq(currentPersonId))
					.orderBy(family.familyName.asc())
					.fetch();

		} catch (Exception e) {
			log.error("Ошибка при получении списка семейных групп, где пользователя с ID {} владелец: {}",
					  currentPersonId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID familyId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPADeleteClause(session, QFamily.family)
					.where(QFamily.family.familyId.eq(familyId)
								   .and(QFamily.family.creator.personId.eq(currentPersonId)))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных семейной группы с  ID {}: {}",
					  familyId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
