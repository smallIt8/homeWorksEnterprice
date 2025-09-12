package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.HqlConstant.*;
import static org.example.util.HibernateSessionFactoryUtil.*;

@Slf4j
@RequiredArgsConstructor
public class FamilyRepositoryImpl implements FamilyRepository {

	@Override
	public void create(Family family) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.persist(family);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании семейной группы '{}': {}",
					  family.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Family> families) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

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
			log.error("Ошибка при массовом добавлении семейных групп: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public Optional<Family> findById(UUID familyId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			Family family = session.createQuery(FIND_BY_ID_FAMILY, Family.class)
					.setParameter("familyId", familyId)
					.uniqueResult();
			session.getTransaction().commit();
			return Optional.ofNullable(family);
		} catch (Exception e) {
			log.error("Ошибка при получении данных семейной группы с ID {}: {}",
					  familyId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Family family) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.createQuery(UPDATE_BY_ID_FAMILY)
					.setParameter("name", family.getName())
					.setParameter("id", family.getFamilyId())
					.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных семейной группы с ID {}: {}",
					  family.getFamilyId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Family> findAll(UUID currentPersonId) {
		return List.of();
	}

	@Override
	public List<Family> findAllByOwner(UUID currentPersonId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			List<Family> families = session.createQuery(FIND_BY_CREATOR_ALL_FAMILY, Family.class)
					.setParameter("personId", currentPersonId)
					.list();
			session.getTransaction().commit();
			return families;
		} catch (Exception e) {
			log.error("Ошибка при получении списка семейных групп пользователя с ID {}: {}",
					  currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID familyId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.createQuery(DELETE_BY_ID_FAMILY)
					.setParameter("familyId", familyId)
					.setParameter("personId", currentPersonId)
					.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных семейной группы с  ID {}: {}",
					  familyId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}