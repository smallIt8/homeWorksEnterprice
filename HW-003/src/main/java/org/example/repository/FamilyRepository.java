package org.example.repository;

import org.example.model.Family;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

public interface FamilyRepository extends ComponentRepository<Family, UUID> {

	List<Family> getAllByOwner(UUID currentPersonId);

}