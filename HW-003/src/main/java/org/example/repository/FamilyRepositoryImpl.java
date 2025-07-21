package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.model.Person;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.GET_BY_ID_FAMILY;

@Slf4j
@RequiredArgsConstructor

public class FamilyRepositoryImpl implements FamilyRepository {

	@Override
	public void create(Family value) {

	}

	public Optional<Family> getById(UUID familyId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(GET_BY_ID_FAMILY)) {
			statement.setObject(1, familyId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Family family = new Family();
					family.setFamilyId(familyId);
					family.setFamilyName(query.getString("family_name"));
					return Optional.of(family);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных сотрудника: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public List<Family> getAllByOwner(Person currentPerson) {
		return List.of();
	}

	@Override
	public void update(Family value) {

	}

	@Override
	public void delete(UUID value) {

	}
}
