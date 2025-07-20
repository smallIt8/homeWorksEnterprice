package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Family;
import org.example.model.Person;
import org.example.repository.FamilyRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

public class FamilyServiceImpl implements FamilyService {
	private final FamilyRepository familyRepository;

	@Override
	public void create() {

	}

	@Override
	public Optional<Family> joinFamily(Person person, Family family) {
		return Optional.empty();
	}

	@Override
	public boolean addMember(String email, UUID person) {
		return false;
	}

	@Override
	public Optional<Family> update(UUID value) {
		return Optional.empty();
	}

	@Override
	public boolean exitFamily(Person person) {
		return false;
	}

	@Override
	public void delete(UUID value) {

	}
}