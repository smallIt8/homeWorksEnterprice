package org.example.repository;

import org.example.model.Family;
import org.example.model.Person;

import java.util.List;
import java.util.UUID;

public interface FamilyRepository extends Repository<Family, UUID> {

	List<Family> getAllByOwner(Person currentPerson);
}