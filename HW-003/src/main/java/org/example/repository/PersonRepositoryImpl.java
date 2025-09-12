package org.example.repository;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Person;
import org.hibernate.Session;

import java.util.Optional;
import java.util.UUID;

import static org.example.model.QPerson.person;
import static org.example.util.HibernateSessionFactoryUtil.openSession;

@Slf4j
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

	@Override
	public Optional<Person> entry(String userName) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Person result = new JPAQuery<Person>(session)
					.select(person)
					.from(person)
					.where(person.userName.eq(userName))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при авторизации пользователя с именем {}: {}",
					  userName, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void create(Person person) {
		try (Session session = openSession()) {
			session.getTransaction().begin();
			session.persist(person);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при создании пользователя '{}': {}",
					  person.getUserName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public boolean checkUserName(String userName) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Long result = new JPAQuery<Long>(session)
					.select(person.count())
					.from(person)
					.where(person.userName.eq(userName.toLowerCase()))
					.fetchOne();

			session.getTransaction().commit();
			return result != null && result > 0;
		} catch (Exception e) {
			log.error("Ошибка при проверке введенного имени пользователя '{}': {}",
					  userName, e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public boolean checkEmail(String email) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Long result = new JPAQuery<Long>(session)
					.select(person.count())
					.from(person)
					.where(person.email.eq(email.toLowerCase()))
					.fetchOne();

			session.getTransaction().commit();
			return result != null && result > 0;
		} catch (Exception e) {
			log.error("Ошибка при проверки введенного email '{}': {}",
					  email, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Person> findById(UUID personId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Person result = new JPAQuery<Person>(session)
					.select(person)
					.from(person)
					.where(person.personId.eq(personId))
					.fetchOne();

			session.getTransaction().commit();
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("Ошибка при получении данных пользователя с ID {}: {}",
					  personId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Person currentPerson) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPAUpdateClause(session, person)
					.where(person.personId.eq(currentPerson.getPersonId()))
					.set(person.firstName, currentPerson.getFirstName())
					.set(person.lastName, currentPerson.getLastName())
					.set(person.email, currentPerson.getEmail())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении данных пользователя с ID {}: {}",
					  currentPerson.getPersonId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public boolean checkUpdateEmail(String email, UUID excludeId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			Long result = new JPAQuery<Long>(session)
					.select(person.count())
					.from(person)
					.where(person.email.eq(email.toLowerCase())
								   .and(person.personId.ne(excludeId)))
					.fetchOne();

			session.getTransaction().commit();
			return result != null && result > 0;
		} catch (Exception e) {
			log.error("Ошибка при проверки введенного email при обновлении '{}': {}",
					  email, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void updatePassword(Person currentPerson) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPAUpdateClause(session, person)
					.where(person.personId.eq(currentPerson.getPersonId()))
					.set(person.password, currentPerson.getPassword())
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при обновлении пароля пользователя с ID {}: {}",
					  currentPerson.getPersonId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID currentPersonId) {
		try (Session session = openSession()) {
			session.getTransaction().begin();

			new JPADeleteClause(session, person)
					.where(person.personId.eq(currentPersonId))
					.execute();

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Ошибка при удалении данных пользователя с  ID {}: {}",
					  currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}