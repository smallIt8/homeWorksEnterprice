package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.HibernateSessionFactoryUtil.*;

@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

	@Override
	public void create(Category category) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.persist(category);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании категории '{}': {}",
					  category.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Category> categories) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			for (int i = 0; i < categories.size(); i++) {
				session.persist(categories.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}

			session.getTransaction().commit();
			log.info("Добавлено категорий: {}", categories.size());
		} catch (Exception e) {
			log.error("Ошибка при массовом добавлении категорий: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Category> findById(UUID categoryId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			var cb = session.getCriteriaBuilder();
			var criteria = cb.createQuery(Category.class);
			var category = criteria.from(Category.class);

			criteria.select(category).where(
					cb.equal(category.get(Category_.categoryId), categoryId)
			);
			session.getTransaction().commit();
			return session.createQuery(criteria)
					.uniqueResultOptional();
		} catch (Exception e) {
			log.error("Ошибка при получении данных категории с ID {}: {}",
					  categoryId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Category category) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			var cb = session.getCriteriaBuilder();
			var criteria = cb.createCriteriaUpdate(Category.class);
			var root = criteria.from(Category.class);

			criteria.set(root.get(Category_.name), category.getName())
					.where(
							cb.equal(root.get(Category_.categoryId), category.getCategoryId())
					);

			session.createMutationQuery(criteria).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных категории с ID {}: {}",
					  category.getCategoryId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Category> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {
			var cb = session.getCriteriaBuilder();
			var criteria = cb.createQuery(Category.class);
			var category = criteria.from(Category.class);

			criteria.select(category)
					.where(cb.equal(category.get(Category_.creator).get(Person_.personId), currentPersonId))
					.orderBy(cb.asc(category.get(Category_.name)));

			return session.createQuery(criteria)
					.list();
		} catch (Exception e) {
			log.error("Ошибка при получении списка категорий для пользователя '{}': {}",
					  currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID categoryId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();
			var cb = session.getCriteriaBuilder();
			var criteria = cb.createCriteriaDelete(Category.class);
			var category = criteria.from(Category.class);

			criteria.where(
					cb.and(
							cb.equal(category.get(Category_.categoryId), categoryId),
							cb.equal(category.get(Category_.creator).get(Person_.personId), currentPersonId)
					)
			);

			session.createMutationQuery(criteria).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных категории с  ID {}: {}",
					  categoryId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}