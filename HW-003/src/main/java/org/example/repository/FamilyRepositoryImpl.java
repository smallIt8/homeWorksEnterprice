package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Family;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.GET_BY_ID_FAMILY;
import static org.example.util.constant.SqlConstant.UPDATE_FAMILY_BY_ID_PERSON;

@Slf4j
@RequiredArgsConstructor
public class FamilyRepositoryImpl implements FamilyRepository {

	@Override
	public void create(Family family) {

	}

	@Override
	public void createBatch(List<Family> families) {

	}

	public Optional<Family> findById(UUID entity) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(GET_BY_ID_FAMILY)) {
			statement.setObject(1, entity);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Family family = new Family();
					family.setFamilyId(entity);
					family.setName(query.getString("family_name"));
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
	public List<Family> findAll(UUID currentPersonId) {
		return List.of();
	}

	@Override
	public List<Family> getAllByOwner(UUID currentPersonId) {
		return List.of();
	}


	@Override
	public void update(Family family) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_FAMILY_BY_ID_PERSON)) {
			UUID familyId = family != null ? family.getFamilyId() : null;
			statement.setObject(1, familyId);
			if (family != null) {
				statement.setObject(2, family.getCreator().getPersonId());
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении семьи пользователя: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(UUID familyId, UUID currentPersonId) {

	}
}