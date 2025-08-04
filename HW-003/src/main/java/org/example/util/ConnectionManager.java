package org.example.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@UtilityClass

public class ConnectionManager {
	private static final String URL_KEY = "db.url";
	private static final String USERNAME_KEY = "db.username";
	private static final String PASSWORD_KEY = "db.password";

	static {
		try {
			Class.forName("org.postgresql.Driver");
			log.info("PostgresSQL драйвер загружен");
		} catch (ClassNotFoundException e) {
			log.error("Не удалось загрузить PostgresSQL драйвер", e);
			throw new RuntimeException(e);
		}
	}

	public Connection open() {
		try {
			return DriverManager.getConnection(
					PropertyUtil.get(URL_KEY),
					PropertyUtil.get(USERNAME_KEY),
					PropertyUtil.get(PASSWORD_KEY));
		} catch (SQLException e) {
			log.error("Ошибка открытия соединения с базой данных", e);
			throw new RuntimeException(e);
		}
	}
}