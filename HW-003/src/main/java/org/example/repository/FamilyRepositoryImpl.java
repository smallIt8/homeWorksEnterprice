package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.util.constant.SqlConstant.*;

@Slf4j
@RequiredArgsConstructor
public class FamilyRepositoryImpl implements FamilyRepository {

	@Override
	public void create(Family family) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_FAMILY)) {
			statement.setObject(1, family.getFamilyId());
			statement.setString(2, family.getName());
			statement.setObject(3, family.getCreator().getPersonId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при создании семейной группы '{}': {}", family.getName(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void createBatch(List<Family> families) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(CREATE_FAMILY)) {
			for (Family family : families) {
				statement.setObject(1, family.getFamilyId());
				statement.setString(2, family.getName());
				statement.setObject(3, family.getCreator().getPersonId());
				statement.addBatch();
			}
			int[] result = statement.executeBatch();
			log.info("Добавлено семейных групп: {}", result.length);
		} catch (SQLException e) {
			log.error("Ошибка при массовом добавлении семейных групп: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public Optional<Family> findById(UUID familyId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_FAMILY)) {
			statement.setObject(1, familyId);
			try (ResultSet query = statement.executeQuery()) {
				if (query.next()) {
					Family family = Family.builder()
							.familyId(familyId)
							.name(query.getString("family_name"))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					return Optional.of(family);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении данных семейной группы с ID {}: {}", familyId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public void update(Family family) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_FAMILY)) {
			statement.setString(1, family.getName());
			statement.setObject(2, family.getFamilyId());
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при обновлении данных семейной группы с ID {}: {}", family.getFamilyId(), e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<Family> findAll(UUID currentPersonId) {
		return List.of();
	}

	@Override
	public List<Family> findAllByOwner(UUID currentPersonId) {
		List<Family> families = new ArrayList<>();
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_CREATOR_ALL_FAMILY)) {
			statement.setObject(1, currentPersonId);
			try (ResultSet query = statement.executeQuery()) {
				while (query.next()) {
					Family family = Family.builder()
							.familyId(query.getObject("family_id", UUID.class))
							.name(query.getString("family_name"))
							.creator(Person.builder()
											 .personId(query.getObject("person_id", UUID.class))
											 .build()
							)
							.build();
					families.add(family);
				}
			}
		} catch (SQLException e) {
			log.error("Ошибка при получении списка семейных групп пользователя с ID {}: {}", currentPersonId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return families;
	}

	@Override
	public void delete(UUID familyId, UUID currentPersonId) {
		try (Connection connection = ConnectionManager.open();
			 PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_FAMILY)) {
			statement.setObject(1, familyId);
			statement.setObject(2, currentPersonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Ошибка при удалении данных семейной группы с  ID {}: {}", familyId, e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}