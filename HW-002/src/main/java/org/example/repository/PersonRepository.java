package org.example.repository;

import org.example.model.Person;

import java.util.UUID;

public interface PersonRepository extends Repository<Person, UUID> {
    boolean checkEmail(String email);

    boolean checkEmail(String email, UUID currentPersonId);
}