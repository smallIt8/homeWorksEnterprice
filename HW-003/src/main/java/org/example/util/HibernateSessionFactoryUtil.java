package org.example.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
@UtilityClass
public class HibernateSessionFactoryUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			var configuration = buildConfiguration();
			configuration.configure();
			sessionFactory = configuration.buildSessionFactory();
			log.info("SessionFactory успешно создана");
		} catch (Exception e) {
			log.error("Ошибка при инициализации SessionFactory: '{}'",
					  e.getMessage(),
					  e);
			throw new ExceptionInInitializerError(e);
		}
	}

	private static Configuration buildConfiguration() {
		return new Configuration();
	}

	public static Session openSession() {
		log.info("Открытие новой Hibernate Session");
		return sessionFactory.openSession();
	}

	public static void close() {
		if (sessionFactory != null && sessionFactory.isOpen()) {
			try {
				sessionFactory.close();
				log.info("SessionFactory успешно закрыта");
			} catch (Exception e) {
				log.error("Ошибка при закрытии SessionFactory: '{}'",
						  e.getMessage(),
						  e);
			}
		}
	}
}
