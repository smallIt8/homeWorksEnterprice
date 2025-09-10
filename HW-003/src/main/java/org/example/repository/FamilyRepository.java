package org.example.repository;

import org.example.model.Family;

import java.util.List;
import java.util.UUID;

public interface FamilyRepository extends ComponentRepository<Family, UUID> {

	List<Family> findAllByOwner(UUID currentPersonId);

}