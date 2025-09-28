package org.example.repository;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Category;
import org.example.model.QCategory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.model.QCategory.category;
import static org.example.util.HibernateSessionFactoryUtil.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	@Override
	public void create(Category category) {
		try (Session session = openSession()) {
			session.beginTransaction();
			session.persist(category);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании категории '{}': {}",
					  category.getCategoryName(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Category> categories) {
		try (Session session = openSession()) {
			session.beginTransaction();

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
			log.error("Ошибка при массовом добавлении категорий: {}",
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Category> findById(UUID categoryId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			var result = new JPAQuery<Category>(session)
					.select(category)
					.from(category)
					.where(category.categoryId.eq(categoryId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных категории с ID {}: {}",
					  categoryId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Category category) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPAUpdateClause(session, QCategory.category)
					.where(QCategory.category.categoryId.eq(category.getCategoryId()))
					.set(QCategory.category.categoryName, category.getCategoryName())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных категории с ID {}: {}",
					  category.getCategoryId(),
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Category> findAll(UUID currentPersonId) {
		try (Session session = openSession()) {

			return new JPAQuery<Category>(session)
					.select(category)
					.from(category)
					.where(category.creator.personId.eq(currentPersonId))
					.orderBy(category.categoryName.asc())
					.fetch();

		} catch (Exception e) {
			log.error("Ошибка при получении списка категорий для пользователя '{}': {}",
					  currentPersonId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID categoryId, UUID currentPersonId) {
		try (Session session = openSession()) {
			session.beginTransaction();

			new JPADeleteClause(session, category)
					.where(category.categoryId.eq(categoryId)
								   .and(category.creator.personId.eq(currentPersonId)))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных категории с  ID {}: {}",
					  categoryId,
					  e.getMessage(),
					  e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
